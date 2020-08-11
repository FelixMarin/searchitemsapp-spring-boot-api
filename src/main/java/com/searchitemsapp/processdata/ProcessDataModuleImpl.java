package com.searchitemsapp.processdata;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.dto.UrlDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Component
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Scope("prototype")
public class ProcessDataModuleImpl extends ProcessDataAbstract implements ProcessDataModule {
	
	private static Map<Integer, Map<String, String>> mapaCookies = Maps.newHashMap(); 
	private UrlDTO urlDto; 
	private String producto;
	private String ordenacion;
	private List<MarcasDTO> listTodasMarcas;
	private Map<Integer,Boolean> mapDynEmpresas;
	
	@Autowired
	private Environment env;

	public  List<ProcessPrice> checkHtmlDocument() 
			throws IOException, URISyntaxException, InterruptedException {
		
		int iIdEmpresa = urlDto.getDidEmpresa();
		List<ProcessPrice> lResultadoDto = Lists.newArrayList();
				
		Map<String, String> mapLoginPageCookies = mapaCookies.get(iIdEmpresa);
		
    	List<Document> listDocuments = getHtmlDocument(urlDto, mapLoginPageCookies, producto, mapDynEmpresas);
    	
    	listDocuments.stream()
    	.filter(document -> Objects.nonNull(document))
    	.forEach(document -> {
    		
            Elements entradas = selectScrapPattern(document,
            		urlDto.getSelectores().getScrapPattern(), 
            		urlDto.getSelectores().getScrapNoPattern());
            
            entradas.stream()
            .filter(elem -> !validaSelector(elem))
            .forEach(elem -> {
            	
            	try {
            		
	    			ProcessPrice ifProcessPrice = fillProcessPrice(elem, urlDto, ordenacion);
	    			
	    			if(validaResultado(iIdEmpresa, ifProcessPrice)) {
	    				lResultadoDto.add(ifProcessPrice);
	    			}
    			
            	}catch(IOException e) {
            		throw new UncheckedIOException(e);
            	}
            });
    	});   
        
        return lResultadoDto;
	}

	private boolean validaResultado(final int iIdEmpresa, 
			final ProcessPrice ifProcessPrice) {
			
		if(StringUtils.isAllEmpty(ifProcessPrice.getNomProducto()) ||
				StringUtils.isAllEmpty(ifProcessPrice.getPrecio()) ||
				StringUtils.isAllEmpty(ifProcessPrice.getPrecioKilo())) {
			return Boolean.FALSE;
		} 
		
		String[] arProducto = producto.split(StringUtils.SPACE);
		Pattern patternProducto = createPatternProduct(arProducto);
		
		String strProducto = filtroMarca(iIdEmpresa, ifProcessPrice.getNomProducto(), listTodasMarcas);
		
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

	@Override
	public List<ProcessPrice> call() throws IOException, URISyntaxException, InterruptedException {
		return checkHtmlDocument();
	}
	
	private boolean validaSelector(Element elem) {
		return Objects.nonNull(elem.selectFirst(env.getProperty("flow.value.pagina.siguiente.carrefour"))) ||
		Objects.nonNull(elem.selectFirst(env.getProperty("flow.value.pagina.acceso.popup.peso")));
	}	
}
