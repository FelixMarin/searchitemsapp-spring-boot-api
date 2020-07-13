package com.searchitemsapp.processdata;

import java.io.IOException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.dto.UrlDTO;
import com.sun.istack.NotNull;

/**
 * Esta clase es la encargada de inicializar el proceso
 * de chequeo de los datos extraidos de las páginas web 
 * rastreadas. El proceso de consulta, extracción y 
 * refinamiento de datos se realiza en tiempo real, lo que
 * permite tener la información totalmente actualizada.
 * {@link ProcessDataAbstract}, {@link ProcessDataLogin}
 * 
 * @author Felix Marin Ramirez
 *
 */
public class ProcessDataModule extends ProcessDataAbstract implements Callable<List<IFProcessPrice>> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataModule.class);  
	
	private static final String SEPARADOR_URL = "%20";
	private static final String SCRAP_NO_PATTERN = "SCRAP_NO_PATTERN";
	private static final String SCRAP_PATTERN = "SCRAP_PATTERN";

	private static Map<Integer, Map<String, String>> mapaCookies = Maps.newHashMap(); 
	private UrlDTO urlDto; 
	private String producto;
	private String ordenacion;
	private Map<String,EmpresaDTO> mapEmpresas;
	private List<MarcasDTO> listTodasMarcas;
	private Map<Integer,Boolean> mapDynEmpresas;
	
	@Autowired
	private Environment env;
	
	public ProcessDataModule(@NotNull UrlDTO urlDto, 
			@NotNull String producto, @NotNull String ordenacion, 
			@NotNull Map<String,EmpresaDTO> mapEmpresas,
			@NotNull List<MarcasDTO> listTodasMarcas,
			@NotNull Map<Integer,Boolean> mapDynEmpresas) {
		super();
		this.urlDto = urlDto;
		this.producto = producto;
		this.ordenacion = ordenacion;
		this.mapEmpresas = mapEmpresas;
		this.listTodasMarcas = listTodasMarcas;
		this.mapDynEmpresas = mapDynEmpresas;
	}
	
	/**
	 * Método que realiza el proceso de web para el procesamiento de datos.
	 * Primero obtiene los html de los sitios web, después
	 * Estrae la información relevante y la inserta en una 
	 * lista de objetos. Finalmente se devuelve una lista
	 * con todos los resultados obtenidos.
	 * 
	 * @return List<IFProcessPrice>
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 */
	public  List<IFProcessPrice> checkHtmlDocument() 
			throws IOException, URISyntaxException, InterruptedException {
		
		List<IFProcessPrice> lResultadoDto;
		Elements entradas;
		IFProcessPrice resDto;
		
		boolean bStatus = urlDto.getBolStatus();
		String[] arProducto = producto.split(StringUtils.SPACE);
		int iIdEmpresa = urlDto.getDidEmpresa();
		Pattern pattern = createPatternProduct(arProducto);
				
        if (getStatus(bStatus) == 200) {
        	
    		Map<String, String> mapLoginPageCookies = mapaCookies.get(iIdEmpresa);
        	
        	List<Document> listDocuments = getHtmlDocument(urlDto, mapLoginPageCookies, producto, mapEmpresas, mapDynEmpresas);
        	
        	lResultadoDto = Lists.newArrayList();
        	
        	for (Document document : listDocuments) {
        	
	        	if(Objects.isNull(document)) {
	            	continue;
	            }

	            if(listDocuments.size() == 1 && 
	            		!validaURL(document.baseUri(),urlDto.getNomUrl()
	            				.replace(StringUtils.SPACE, SEPARADOR_URL))) {
	            	return Lists.newArrayList();
	            }
	          
	            entradas = selectScrapPattern(document,
	            		urlDto.getSelectores().get(SCRAP_PATTERN), 
	            		urlDto.getSelectores().get(SCRAP_NO_PATTERN));

	    		for (Element elem : entradas) {
	    			
	    			if(validaSelector(elem)) {
	    				continue;
	    			}
	    			
	    			resDto = fillProcessPrice(elem, urlDto, ordenacion, 
	    					new ProcessPriceModule(), mapEmpresas);
	    			
	    			if(validaYCargaResultado(iIdEmpresa, arProducto, resDto,  pattern)) {
	    				lResultadoDto.add(resDto);
	    			}
		        }
        	}	
        } else {
        	return Lists.newArrayList();
        }   
        
        return lResultadoDto;
	}

	/**
	 * Método publico desde el que se inicializa el proceso.
	 * Devuelve una lista con los resultado obtenidos.
	 * 
	 * @return List<IFProcessPrice>
	 */
	@Override
	public List<IFProcessPrice> call() throws IOException, URISyntaxException, InterruptedException {
		return checkHtmlDocument();
	}
	
	private int getStatus(final boolean bStatus) {
		return bStatus?getStatusConnectionCode(urlDto.getNomUrl()):200;
	}

	private boolean validaYCargaResultado(@NotNull final int iIdEmpresa, 
			@NotNull final String[] arProducto, 
			@NotNull final IFProcessPrice resDto, 
			@NotNull final Pattern pattern) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		if(Objects.isNull(resDto.getNomProducto()) || iIdEmpresa == 0 ||
				StringUtils.isAllEmpty(resDto.getPrecio()) ||
				Objects.isNull(resDto.getPrecioKilo()) || 
				StringUtils.isAllEmpty(resDto.getPrecioKilo())) {
			return Boolean.FALSE;
		} 
		
		String strProducto = filtroMarca(iIdEmpresa, resDto.getNomProducto(), mapEmpresas, listTodasMarcas, mapDynEmpresas);
		
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
			
			return pattern.matcher(eliminarTildes(strProducto).toUpperCase()).find();
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
}
