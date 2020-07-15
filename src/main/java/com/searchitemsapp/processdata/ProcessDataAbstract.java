package com.searchitemsapp.processdata;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.dto.UrlDTO;
import com.searchitemsapp.impl.IFImplementacion;
import com.searchitemsapp.processdata.empresas.IFProcessDataCondis;
import com.searchitemsapp.processdata.empresas.IFProcessDataEmpresasFactory;
import com.searchitemsapp.processdata.empresas.IFProcessDataEroski;
import com.searchitemsapp.processdata.empresas.IFProcessDataMercadona;
import com.searchitemsapp.processdata.empresas.IFProcessDataSimply;
import com.searchitemsapp.processdata.empresas.ProcessDataEmpresasFactory;



/**
 * Clase abstracta que cotiene métodos 
 * expecializados para módulo de web scraping.
 * <br>
 * {@link Jsoup}
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public abstract class ProcessDataAbstract {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataAbstract.class);   
	
	private static final String AGENT_ALL = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36";
	private static final String REFFERER_GOOGLE = "http://www.google.com";
	private static final String ACCEPT_LANGUAGE = "Accept-Language";	
	private static final String ES_ES = "es-ES,es;q=0.8";
	private static final String ACCEPT_ENCODING = "Accept-Encoding";
	private static final String ACCEPT = "Accept";
	private static final String PROTOCOL_ACCESSOR ="://";
	private static final String GZIP_DEFLATE_SDCH = "gzip, deflate, sdch";
	private static final String ACCEPT_VALUE = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
	private static final String HIPERCOR = "HIPERCOR";
	private static final String ELCORTEINGLES = "ELCORTEINGLES";
	private static final String DIA = "DIA";
	private static final String CONDIS = "CONDIS";
	private static final String MERCADONA = "MERCADONA";
	private static final char CHAR_ENIE_COD = '\u00f1';
	private static final String STRING_ENIE_MIN = "ñ";
	private static final String STRING_ENIE_MAY = "Ñ";
	private static final String UNICODE_ENIE = "u00f1";
	private static final String REEMPLAZABLE_TILDES = "[\\p{InCombiningDiacriticalMarks}]";
	
	private static final String LEFT_PARENTHESIS_0 = " (";
	private static final String DOBLE_BARRA = "//";
	private static final String HTTPS = "https:";
	private static final String BARRA = "/";
	private static final String RIGTH_PARENTHESIS = "\\)";
	private static final String LEFT_PARENTHESIS = "\\(";
	private static final String REGEX_PERCENT_DOLAR = "(\\%|\\$00)";
	private static final String DOT_STRING = ".";
	private static final String COMMA_STRING = ",";
	private static final String PIPE_STRING = "|";
	private static final String SCRIPT = "script";
	
	@Autowired 
	private ProcessDataDynamic procesDataDynamic;
	
	@Autowired
	private IFProcessDataEmpresasFactory processDataEmpresasFactory;
	
	@Autowired
	private IFImplementacion<SelectoresCssDTO, EmpresaDTO> selectoresCssImpl;

	@Autowired
	private IFImplementacion<EmpresaDTO, CategoriaDTO> iFEmpresaImpl;
	
	@Autowired
	private IFImplementacion<MarcasDTO, CategoriaDTO> iFMarcasImp;
		
	@Autowired
	private IFProcessDataMercadona scrapingMercadona;
				
	@Autowired
	private IFProcessDataCondis ifProcessDataCondis;
	
	@Autowired
	private IFProcessDataEroski ifProcessDataEroski;
	
	@Autowired
	private IFProcessDataSimply ifProcessDataSimply;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private EmpresaDTO empresaDto;
	
	@Autowired
	SelectoresCssDTO selectoresCssDto;
	
	protected ProcessDataAbstract() {
		super();
	}
	
	/**
	 * Método que carga los datos estáticos usados en el proceso
	 * de las peticiones. Este método solo se ejecuta una vez,
	 * los datos se cachean mientras esté la aplicación activa.
	 */
	public void applicationData(final Map<String,EmpresaDTO> mapEmpresas, 
			final Map<Integer,Boolean> mapDynEmpresas) {
		
		try {
			List<EmpresaDTO> listEmpresaDto = iFEmpresaImpl.findAll();

			listEmpresaDto.stream().forEach(empresaDTO -> {
				mapEmpresas.put(empresaDTO.getNomEmpresa(), empresaDTO);
				mapDynEmpresas.put(empresaDTO.getDid(), empresaDTO.getBolDynScrap());
			});
			
		}catch(IOException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
	}
	
	public List<MarcasDTO> getListTodasMarcas() throws IOException  {
		return iFMarcasImp.findAll();
	}
	
	public List<SelectoresCssDTO> listSelectoresCssPorEmpresa(
			final String didEmpresas, 
			final String didPais,
			final String didCategoria) 
					throws IOException {

		String emp;
		
		if("ALL".equalsIgnoreCase(didEmpresas)) {
			emp = env.getProperty("flow.value.all.id.empresa");
		} else {
			emp = didEmpresas;
		}
		
		StringTokenizer st = new StringTokenizer(emp, COMMA_STRING); 			
		List<Integer> listaAux = Lists.newArrayList();
		
		while (st.hasMoreElements()) {
			listaAux.add(Integer.parseInt(String.valueOf(st.nextElement())));
			
		}
		
		List<SelectoresCssDTO> listaSelectoresResultado = Lists.newArrayList();
		
		listaAux.forEach(didEmpresa -> {
			
			try {
				empresaDto.setDid(didEmpresa);			
				List<SelectoresCssDTO> lsel = selectoresCssImpl.findByTbSia(selectoresCssDto, empresaDto);
				listaSelectoresResultado.addAll(lsel);
			}catch(IOException e) {
				throw new UncheckedIOException(e);
			}
			
		});
		
		return listaSelectoresResultado;
	}
	
	
	/**
	 * Con esta metodo se comprueba el Status code de la respuesta que recibo al hacer
	 * la peticion EJM: 
	 * 		200 OK 300 Multiple Choices 301 Moved Permanently 305 Use Proxy .
	 * 		400 Bad Request 403 Forbidden 404 Not Found 500 Internal Server Error.
	 * 		502 Bad Gateway 503 Service Unavailable.
	 * 
	 * @param url
	 * @return Status Code
	 * @throws IOException
	 */
	protected int getStatusConnectionCode(final String url) {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		} 
		
		int iResultado = 0;

		try {
			
			iResultado = Jsoup.connect(url)
					.userAgent(AGENT_ALL)
					.method(Connection.Method.GET)
					.referrer(REFFERER_GOOGLE)
					.header(ACCEPT_LANGUAGE, ES_ES)
					.header(ACCEPT_ENCODING, GZIP_DEFLATE_SDCH)
					.header(ACCEPT, ACCEPT_VALUE)
					.maxBodySize(0)
					.timeout(100000)
					.ignoreHttpErrors(Boolean.TRUE)
					.execute()
					.statusCode();
			
		} catch (IOException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		return iResultado;
	}

	/**
	 * Este método devuelve un objeto de la clase Document con el contenido del
	 * HTML de la web que permitirá parsearlo con los métodos de la libreía
	 * JSoup.
	 * 
	 * @param url
	 * @return Documento con el HTML
	 * @throws IOException
	 * @throws URISyntaxException 
	 * @throws InterruptedException 
	 */
	protected List<Document> getHtmlDocument(final UrlDTO urlDto, 
			final Map<String, String> mapLoginPageCookies,
			final String producto,
			final Map<String,EmpresaDTO> mapEmpresas,
			final Map<Integer,Boolean> mapDynEmpresas) 
					throws IOException, URISyntaxException, InterruptedException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
    	List<Document> listDocuments = Lists.newArrayList();
    	
		int idEmpresa = urlDto.getDidEmpresa();			
    	
    	Document document = getDocument(urlDto.getNomUrl(), idEmpresa, 
    			producto, mapLoginPageCookies, mapEmpresas, mapDynEmpresas);

    	List<String> liUrlsPorEmpresaPaginacion = urlsPaginacion(document, urlDto, idEmpresa);
   		 			
   		if(!liUrlsPorEmpresaPaginacion.isEmpty()) {
	   		for (String url : liUrlsPorEmpresaPaginacion) {
	   			listDocuments.add(getDocument(url, idEmpresa, 
	   					producto, mapLoginPageCookies, mapEmpresas, mapDynEmpresas));
			}
   		} else {
   			listDocuments.add(document);
   		}
		      	
		return listDocuments;
	}
	
	/**
	 * Devuelve un listado con de las URLs correspondientes
	 * a uno de los supermecados a los que se le van a 
	 * extraer los datos.
	 * 
	 * {@link ProcessDataEmpresasFactory#getScrapingEmpresa(int)}
	 * 
	 * @param document
	 * @param urlDto
	 * @param selectorCssDto
	 * @param idEmpresa
	 * @return List<String>
	 * @throws MalformedURLException
	 */
	protected List<String> urlsPaginacion(final Document document, 
			final UrlDTO urlDto, final int idEmpresa) 
					throws MalformedURLException {
		
		List<String> listUrlsResultado = Lists.newArrayList();
		
		listUrlsResultado.addAll(processDataEmpresasFactory
				.getScrapingEmpresa(idEmpresa).getListaUrls(document, urlDto));
		
		return listUrlsResultado;
	}
	
	/**
	 * Este método extrae la informacíon a partir
	 * de un selector. Devuelve un conjunto de 
	 * elementos que coincidan con el patron de
	 * busqeda.
	 * 
	 * @param document
	 * @param strScrapPattern
	 * @param strScrapNotPattern
	 * @return Elements
	 */
	protected Elements selectScrapPattern(final Document document,
			final String strScrapPattern, final String strScrapNotPattern) {

		Elements entradas;

        if(Objects.isNull(strScrapNotPattern)) {
        	entradas = document.select(strScrapPattern);
        } else {
        	entradas = document.select(strScrapPattern).not(strScrapNotPattern);
        }

        return entradas;
	}

	/**
	 * Método que valida el resultado obtenido. 
	 * 
	 * @param arProducto
	 * @param nomProducto
	 * @param iIdEmpresa
	 * @param pattern
	 * @return boolean
	 */
	protected String eliminarTildes(final String cadena) {
			
		if(cadena.indexOf(CHAR_ENIE_COD) != -1) {
			return cadena;
		}
		
		String resultado = cadena.replace(STRING_ENIE_MAY, UNICODE_ENIE);
		resultado = Normalizer.normalize(resultado.toLowerCase(), Normalizer.Form.NFD);
		resultado = resultado.replaceAll(REEMPLAZABLE_TILDES, StringUtils.EMPTY);
		resultado = resultado.replace(UNICODE_ENIE, STRING_ENIE_MIN);
		return Normalizer.normalize(resultado, Normalizer.Form.NFC);
		
	}
	
	/**
	 * Método que compone un patrón regex con el que se
	 * realizará el filtrado de productos a partir del 
	 * parámetro de entrada.
	 * 
	 * @param arProducto
	 * @return Pattern
	 */
	protected Pattern createPatternProduct(final String[] arProducto) {

		List<String> tokens = Lists.newArrayList();
		
		List<String> listProducto = Arrays.asList(arProducto);  
		listProducto.forEach(elem -> {
			tokens.add(elem.toUpperCase());
		});
		
		StringBuilder stringBuilder = new StringBuilder(10);
		
		stringBuilder.append("(");
		
		tokens.forEach(e -> {
			stringBuilder.append(".*").append(e);
		});
		
		stringBuilder.append(")");
		
		Collections.reverse(tokens);
		
		stringBuilder.append("|(");
		
		tokens.forEach(e -> {
			stringBuilder.append(".*").append(e);			
		});

		stringBuilder.append(")");
		
		return Pattern.compile(stringBuilder.toString());
	}
	
	/**
	 * Método encargado de filtra la marca del producto. 
	 * Elimina la marca de la descripción del producto.
	 * 
	 * @param iIdEmpresa
	 * @param nomProducto
	 * @return String
	 */
	protected String filtroMarca(
			final int iIdEmpresa, 
			final String nomProducto, 
			final Map<String,EmpresaDTO> mapEmpresas,
			final List<MarcasDTO> listTodasMarcas,
			final Map<Integer,Boolean> mapDynEmpresas) {
		
		String strProducto;
		
		if(iIdEmpresa == mapEmpresas.get(HIPERCOR).getDid() ||
				iIdEmpresa == mapEmpresas.get(DIA).getDid() ||
				iIdEmpresa == mapEmpresas.get(ELCORTEINGLES).getDid()) {
			strProducto = eliminarMarcaPrincipio(nomProducto);
		} else {
			strProducto = nomProducto;
		}
		
		for (MarcasDTO marcaDto : listTodasMarcas) {
			if(strProducto.toLowerCase()
					.startsWith(marcaDto
							.getNomMarca()
							.toLowerCase())) {
				
				strProducto = strProducto.toLowerCase()
						.replaceAll(marcaDto.getNomMarca()
							.toLowerCase(), StringUtils.EMPTY).trim();
				break;
			}
		}
		return strProducto;
	}
	
	/**
	 * Este método se encarga de extraer cada uno de los datos 
	 * del producto para componer un objeto de tipo IFProcessPrice.
	 * 
	 * @param elem
	 * @param selectoresCssDto
	 * @param urlDto
	 * @param ordenacion
	 * @return IFProcessPrice
	 * @throws IOException
	 */
	protected IFProcessPrice fillProcessPrice(final Element elem,
			final UrlDTO urlDto, 
			final String ordenacion, 
			IFProcessPrice ifProcessPrice, 
			final Map<String,EmpresaDTO> mapEmpresas) throws IOException {

		int idEmpresaActual = urlDto.getDidEmpresa();

		ifProcessPrice.setImagen(elementoPorCssSelector(elem, urlDto.getSelectores().getSelImage(), urlDto, mapEmpresas));
		ifProcessPrice.setNomProducto(elementoPorCssSelector(elem, urlDto.getSelectores().getSelProducto(), urlDto, mapEmpresas));
		ifProcessPrice.setDesProducto(elementoPorCssSelector(elem, urlDto.getSelectores().getSelProducto(), urlDto, mapEmpresas));
		ifProcessPrice.setPrecio(elementoPorCssSelector(elem, urlDto.getSelectores().getSelPrecio(), urlDto, mapEmpresas));
		ifProcessPrice.setPrecioKilo(elementoPorCssSelector(elem, urlDto.getSelectores().getSelPreKilo(), urlDto, mapEmpresas));
		ifProcessPrice.setNomUrl(elementoPorCssSelector(elem, urlDto.getSelectores().getSelLinkProd(), urlDto, mapEmpresas));
		ifProcessPrice.setDidEmpresa(urlDto.getDidEmpresa());
		ifProcessPrice.setNomEmpresa(urlDto.getNomEmpresa());

		if(idEmpresaActual == mapEmpresas.get(MERCADONA).getDid()) {
			ifProcessPrice.setNomUrlAllProducts(scrapingMercadona.getUrlAll(ifProcessPrice));
			ifProcessPrice.setImagen(ifProcessPrice.getImagen().replace(COMMA_STRING, DOT_STRING));
		}else {
			ifProcessPrice.setNomUrlAllProducts(urlDto.getNomUrl());
		}
		
		ifProcessPrice.setOrdenacion(Integer.parseInt(ordenacion));		
		
		return ifProcessPrice;
	}
	
	/**
	 * Funcionalidad que se encaga de corregir el nombre del producto
	 * en el caso de contenga espacios en blanco, caracteres especiales
	 * o palabras reservadas no permitidas.
	 * 
	 * @param producto
	 * @return String
	 * @throws IOException
	 */
	protected String tratarProducto(final String producto) throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		String productoTratado=anadirCaracteres(producto, Character.MIN_VALUE, 0, 0);
		productoTratado=anadirCaracteres(productoTratado, Character.MIN_VALUE, 0, 1);
		
		Matcher matcher = Pattern.compile(REGEX_PERCENT_DOLAR).matcher(productoTratado);
		
		if(matcher.find()) {
			return productoTratado;
		} else {
			return URLEncoder.encode(productoTratado, StandardCharsets.UTF_8.toString());
		}
	}
	
	protected String reeplazarTildesCondis(final String producto) {
		return ifProcessDataCondis.eliminarTildesProducto(producto);
	}
	
	protected String reeplazarCaracteresCondis(final String producto) {
		return ifProcessDataCondis.reemplazarCaracteres(producto);
	}
	
	protected String reemplazarCaracteresEroski(final String producto) {
		return ifProcessDataEroski.reemplazarCaracteres(producto);
	}
	
	protected String reeplazarCaracteresSimply(final String producto) {
		return ifProcessDataSimply.reemplazarCaracteres(producto);
	}
		
	/**
	 * Este método devuelve un objeto de la clase Document con el contenido del
	 * HTML de la web.
	 * 
	 * @param strUrl
	 * @param didEmpresa
	 * @param producto
	 * @param mapLoginPageCookies
	 * @return Document
	 * @throws InterruptedException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	private Document getDocument(final String strUrl, 
			final int didEmpresa, final String producto,
			final Map<String, String> mapLoginPageCookies,
			final Map<String,EmpresaDTO> mapEmpresas,
			final Map<Integer,Boolean> mapDynEmpresas) 
					throws InterruptedException, URISyntaxException, IOException {
	
		Document document = null;
		Connection connection  = null;
		Response response = null;
		
		boolean isMercadona = didEmpresa == mapEmpresas.get(MERCADONA).getDid();	
		boolean bDynScrap = mapDynEmpresas.get(didEmpresa);
		URL url = new URL(strUrl);

		if(bDynScrap) {			
			document = Jsoup.parse(procesDataDynamic
					.getDynHtmlContent(strUrl, didEmpresa), 
					url.toURI().toString());
		} else if(isMercadona) {			
			connection = scrapingMercadona.getConnection(strUrl, producto);	
			response = connection.execute();
		} else {			
			connection = Jsoup.connect(strUrl)
					.userAgent(AGENT_ALL)
					.method(Connection.Method.GET)
					.referrer(url.getProtocol().concat(PROTOCOL_ACCESSOR).concat(url.getHost().concat("/")))
					.ignoreContentType(Boolean.TRUE)
					.header(ACCEPT_LANGUAGE, ES_ES)
					.header(ACCEPT_ENCODING, GZIP_DEFLATE_SDCH)
					.header(ACCEPT, ACCEPT_VALUE)
					.maxBodySize(0)
					.timeout(100000);
			response = connection.execute();
		}

		if(!bDynScrap && Objects.nonNull(mapLoginPageCookies)) {
			connection.cookies(mapLoginPageCookies);
		}
		
		if(bDynScrap) {
			return document;
		} else if(isMercadona) {
       		return scrapingMercadona.getDocument(strUrl, response.body());
       	} else {
			return response.parse();
		}
	}
	
	/**
	 * Extrae la marca del principio de los resultado para
	 * no distorsionar el resultado.
	 * 
	 * @param nomProducto
	 * @return
	 */
	private String eliminarMarcaPrincipio(final String nomProducto) {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		String[] nomProdSeparado = nomProducto.trim().split(StringUtils.SPACE);
				
		StringBuilder stringBuilder = new StringBuilder(10);
		
		for (int i = 0; i < nomProdSeparado.length; i++) {
			
			String may = nomProdSeparado[i].toUpperCase();			
			if(nomProdSeparado[i].equals(may)) {
				continue;
			}
			
			stringBuilder.append(nomProdSeparado[i]).append(StringUtils.SPACE);
		}
		
		return stringBuilder.toString();
	}	
	
	/**
	 * Método que extrae los datos del elemento a partir de
	 * un selector. Dependiendo de la empresa la técnica de
	 * extraccion de los datos es de una manera u otra.
	 * 
	 * @param elem
	 * @param cssSelector
	 * @param urlDto
	 * @return String
	 * @throws MalformedURLException
	 */
	private String elementoPorCssSelector(final Element elem, 
			final String cssSelector,
			final UrlDTO urlDto,
			final Map<String,EmpresaDTO> mapEmpresas) throws MalformedURLException {
				
		List<String> lista = Lists.newArrayList();
		String strResult;
		
		StringTokenizer st = new StringTokenizer(cssSelector,PIPE_STRING);  
		
		while (st.hasMoreTokens()) {  
			lista.add(st.nextToken());
		}
		
		int listaSize = lista.size();
		
		if(mapEmpresas.get(MERCADONA).getDid().equals(urlDto.getDidEmpresa())) {
			
			strResult = scrapingMercadona.getResult(elem, cssSelector);
			
		} else if(mapEmpresas.get(CONDIS).getDid().equals(urlDto.getDidEmpresa()) &&
				SCRIPT.equalsIgnoreCase(lista.get(0))) {	
			
			strResult = ifProcessDataCondis.tratarTagScript(elem, lista.get(0));
			
		} else if(mapEmpresas.get(ELCORTEINGLES).getDid().equals(urlDto.getDidEmpresa())) {
			
			strResult = elem.selectFirst(env
					.getProperty("flow.value.pagina.precio.eci.offer")).text();
		
		} else {
			
			strResult = extraerValorDelElemento(listaSize, elem, lista, cssSelector);
		
		}
		
		return validaResultadoElementValue(strResult, urlDto.getNomUrl());
	}
	
	/**
	 * Valida los datos extraidos de los elementos.
	 * 
	 * @param strResult
	 * @param strUrl
	 * @return String
	 * @throws MalformedURLException
	 */
	private String validaResultadoElementValue(String strResult, 
			final String strUrl) throws MalformedURLException {
		
		int iend = -1;
		
		if(Objects.isNull(strResult)){
			return strResult;
		} else {
			iend = strResult.indexOf(LEFT_PARENTHESIS_0);
		}
				
		URL url = new URL(strUrl);
		String strUrlEmpresa = url.getProtocol().concat(PROTOCOL_ACCESSOR).concat(url.getHost());
		
		if(iend != -1) {
			strResult = strResult.substring(0, 
					strResult.indexOf(LEFT_PARENTHESIS_0)-1);
		}
		
		String caracteres = StringUtils.EMPTY;
		if(Objects.nonNull(strResult) && strResult.trim().startsWith(DOBLE_BARRA)) {
			caracteres = HTTPS.concat(strResult);
		} else if(Objects.nonNull(strResult) && strResult.trim().startsWith(BARRA)) {
			caracteres = strUrlEmpresa.concat(strResult); 
		} else if(Objects.nonNull(strResult)){
			caracteres = strResult;
		}
		 
		String resultado = caracteres.replaceAll(LEFT_PARENTHESIS, StringUtils.EMPTY);
		resultado = resultado.replaceAll(RIGTH_PARENTHESIS, StringUtils.EMPTY);
		
		resultado = resultado.replace("€", " eur");
		resultado = resultado.replace("Kilo", "kg");
		resultado = resultado.replace(" / ", "/");
		resultado = resultado.replace(" \"", "\"");
		 
		return resultado;
	}	
	
	/**
	 * Realiza la adicion de caracteres a una cadena para proporcionarle una
	 * longitud determinada
	 *
	 * @param pstrCadena   cadena
	 * @param piLongitud   Longitud de la cadena
	 * @param pchrCaracter caracter
	 * @param piTipo       Tipo de adicion (Izquierda o Derecha)
	 * @return cadena ajustada
	 * @modelguid {360E79DB-48FE-4795-9E71-37FCA8F0B22D}
	 */
	private String anadirCaracteres(String strCadena, char chrCaracter, int iLongitud, int iTipo) {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		if(StringUtils.isAllEmpty(strCadena)) {
			return StringUtils.EMPTY;
		}
		
		StringBuilder stringBuilder = new StringBuilder(10);
		
		if (iTipo == 0) {
			stringBuilder.append(strCadena);
		}
		
		final int dif = iLongitud - strCadena.length();
		
		for (int i = 0; i < dif; i++) {
			stringBuilder.append(chrCaracter);
		}

		if (iTipo == 1) {
			stringBuilder.append(strCadena);
		}

		return stringBuilder.toString();
	}
	
	private String extraerValorDelElemento(int l,Element elem,
			List<String> lista,String cssSelector) {
		
		switch (l) {
		case 1:
			return elem.select(lista.get(0)).text();
		case 2:
			return elem.select(lista.get(0)).attr(lista.get(1));
		default:
			return elem.select(cssSelector).text();
		}
	}
}
