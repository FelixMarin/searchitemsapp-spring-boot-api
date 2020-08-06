package com.searchitemsapp.processdata;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.dto.UrlDTO;

@Component
@Scope("prototype")
public class ProcessDataModule extends ProcessDataAbstract implements Callable<List<IFProcessPrice>> {
	
	private static final String SEPARADOR_URL = "%20";

	private static Map<Integer, Map<String, String>> mapaCookies = Maps.newHashMap(); 
	private UrlDTO urlDto; 
	private String producto;
	private String ordenacion;
	private Map<String,EmpresaDTO> mapEmpresas;
	private List<MarcasDTO> listTodasMarcas;
	private Map<Integer,Boolean> mapDynEmpresas;
	
	@Autowired
	private Environment env;

	public ProcessDataModule() {
		super();
	}

	public  List<IFProcessPrice> checkHtmlDocument() 
			throws IOException, URISyntaxException, InterruptedException {
		
		String[] arProducto = producto.split(StringUtils.SPACE);
		int iIdEmpresa = urlDto.getDidEmpresa();
		Pattern patternProducto = createPatternProduct(arProducto);
		List<IFProcessPrice> lResultadoDto = Lists.newArrayList();
				
		Map<String, String> mapLoginPageCookies = mapaCookies.get(iIdEmpresa);
		
    	List<Document> listDocuments = getHtmlDocument(urlDto, mapLoginPageCookies, producto, mapEmpresas, mapDynEmpresas);
    	
    	listDocuments.stream()
    	.filter(document -> Objects.nonNull(document))
    	.filter(document -> validaURL(document.baseUri(),urlDto.getNomUrl().replace(StringUtils.SPACE, SEPARADOR_URL)))        	
    	.forEach(document -> {
    		
            Elements entradas = selectScrapPattern(document,
            		urlDto.getSelectores().getScrapPattern(), 
            		urlDto.getSelectores().getScrapNoPattern());
            
            entradas.stream()
            .filter(elem -> !validaSelector(elem))
            .forEach(elem -> {
            	
            	try {
            		
	    			IFProcessPrice ifProcessPrice = fillProcessPrice(elem, urlDto, ordenacion, 
	    					new ProcessPriceModule(), mapEmpresas);
	    			
	    			if(validaYCargaResultado(iIdEmpresa, arProducto, ifProcessPrice,  patternProducto)) {
	    				lResultadoDto.add(ifProcessPrice);
	    			}
    			
            	}catch(IOException e) {
            		throw new UncheckedIOException(e);
            	}
            });
    	});   
        
        return lResultadoDto;
	}

	@Override
	public List<IFProcessPrice> call() throws IOException, URISyntaxException, InterruptedException {
		return checkHtmlDocument();
	}

	private boolean validaYCargaResultado(final int iIdEmpresa, 
			final String[] arProducto, 
			final IFProcessPrice ifProcessPrice, 
			final Pattern patternProducto) {
			
		if(Objects.isNull(ifProcessPrice.getNomProducto()) || iIdEmpresa == 0 ||
				StringUtils.isAllEmpty(ifProcessPrice.getPrecio()) ||
				Objects.isNull(ifProcessPrice.getPrecioKilo()) || 
				StringUtils.isAllEmpty(ifProcessPrice.getPrecioKilo())) {
			return Boolean.FALSE;
		} 
		
		String strProducto = filtroMarca(iIdEmpresa, ifProcessPrice.getNomProducto(), mapEmpresas, listTodasMarcas);
		
		if(StringUtils.isAllBlank(strProducto)) {
			return Boolean.FALSE;
		}
		
		if(arProducto.length == 1 &&
				eliminarTildes(strProducto)
				.toLowerCase().startsWith(
						eliminarTildes(arProducto[0].trim())
				.toLowerCase().concat(StringUtils.SPACE))) {
			return Boolean.TRUE;			
		} else if(arProducto.length > 1) {			
			
			return patternProducto.matcher(eliminarTildes(strProducto).toUpperCase()).find();
		} else {
			return Boolean.FALSE;
		}
	}

	private boolean validaURL(final String baseUri,final String url) {
		return url.equalsIgnoreCase(baseUri);
	}
	
	private boolean validaSelector(Element elem) {
		return Objects.nonNull(elem.selectFirst(env.getProperty("flow.value.pagina.siguiente.carrefour"))) ||
		Objects.nonNull(elem.selectFirst(env.getProperty("flow.value.pagina.acceso.popup.peso")));
	}	
	
	public UrlDTO getUrlDto() {
		return urlDto;
	}

	public void setUrlDto(UrlDTO urlDto) {
		this.urlDto = urlDto;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getOrdenacion() {
		return ordenacion;
	}

	public void setOrdenacion(String ordenacion) {
		this.ordenacion = ordenacion;
	}

	public Map<String, EmpresaDTO> getMapEmpresas() {
		return mapEmpresas;
	}

	public void setMapEmpresas(Map<String, EmpresaDTO> mapEmpresas) {
		this.mapEmpresas = mapEmpresas;
	}

	public List<MarcasDTO> getListTodasMarcas() {
		return listTodasMarcas;
	}

	public void setListTodasMarcas(List<MarcasDTO> listTodasMarcas) {
		this.listTodasMarcas = listTodasMarcas;
	}

	public Map<Integer, Boolean> getMapDynEmpresas() {
		return mapDynEmpresas;
	}

	public void setMapDynEmpresas(Map<Integer, Boolean> mapDynEmpresas) {
		this.mapDynEmpresas = mapDynEmpresas;
	}
}
