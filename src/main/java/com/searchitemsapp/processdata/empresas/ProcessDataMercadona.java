package com.searchitemsapp.processdata.empresas;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDTO;
import com.searchitemsapp.processdata.IFProcessPrice;

/**
 * Módulo de scraping especifico diseñado para la 
 * extracción de datos del sitio web de Mercadona.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public class ProcessDataMercadona implements IFProcessDataMercadona {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataMercadona.class);  

	private static final String LTEMGTCIERRE = "&lt;/em&gt;";
	private static final String SEPARADORURL = "%20";
	private static final String LTEMGT = "&lt;em&gt;";
	private static final String PARAMSQUERY = "{\"params\":\"query=";
	private static final String CLICKANALYTICSTRUE = "&clickAnalytics=true\"}";
	private static final String TAGFINROOT = "</root>";
	private static final String TAGINIROOT = "<root>";
	
	private static final String CABECERAXML = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>";
	private static final String REFERRERMERCADONA = "https://tienda.mercadona.es/";
	private static final String AGENTALL = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36";
	private static final String ACCEPTLANGUAGE = "Accept-Language";	
	private static final String ESES = "es-ES,es;q=0.8";
	private static final String ACCEPTENCODING = "Accept-Encoding";
	private static final String ACCEPT = "Accept";
	private static final String ACEPTVALUEJSON = "application/json";
	private static final String GZIPDEFLATESDCH = "gzip, deflate, sdch";	
	private static final String URLALLPRODUCTS = "https://lolamarket.com/tienda/mercadona/buscar/";

	private ProcessDataMercadona() {
		super();
	}
	
	/**
	 * Compone una lista de URLs de la web de Mercadona.
	 * Con estas URLs se realizarán las peticiones al
	 * sitio web para extraer los datos. 
	 * 
	 * @param document
	 * @param urlDto
	 * @param selectorCssDto
	 * @return List<String>
	 * @exception MalformedURLException
	 */
	@Override
	public List<String> getListaUrls(final Document document, final UrlDTO urlDto)
			throws MalformedURLException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		String urlBase = urlDto.getNomUrl();
		
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);
		
		return listaUrls;
	}	

	/**
	 * Metodo encargado de extraer la información util
	 * del documento web extraido del sitio de Mercadona.
	 * 
	 * @param url
	 * @param body
	 * @return Document
	 */
	public Document getDocument(final String url, final String body) {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		JSONObject json = new JSONObject(body);
		
		String xml = XML.toString(json);
		
		xml = xml.replace(".", ",");
		
		if(StringUtils.isAllEmpty(xml)) {
			return new Document(StringUtils.EMPTY);
		} else {
			xml = CABECERAXML.concat(TAGINIROOT).concat(xml).concat(TAGFINROOT);
			xml = xml.replace(LTEMGT, StringUtils.EMPTY);
			xml = xml.replace(LTEMGTCIERRE, StringUtils.EMPTY);
		}
		
		Document doc = Jsoup.parse(xml, StringUtils.EMPTY, Parser.xmlParser());
		doc.setBaseUri(url);
		
		return doc;
	}
	
	/**
	 * Retorna el texto de un elemento obtenido mediate
	 * el selector indicado el los argumentos.
	 * 
	 * @param elem
	 * @param cssSelector
	 * @return String
	 */
	public String getResult(final Element elem, final String cssSelector) {		
		return elem.selectFirst(cssSelector).text();
	}
	
	/**
	 * Retorna la conexión establecida con el sitio web.
	 * 
	 * @param strUrl
	 * @param producto
	 * @return
	 */
	public Connection getConnection(final String strUrl, final String producto) {
		
		return Jsoup.connect(strUrl)
				.userAgent(AGENTALL)
				.method(Connection.Method.POST)
				.referrer(REFERRERMERCADONA)
				.ignoreContentType(Boolean.TRUE)
				.header(ACCEPTLANGUAGE, ESES)
				.header(ACCEPTENCODING, GZIPDEFLATESDCH)
				.header(ACCEPT, ACEPTVALUEJSON)
				.maxBodySize(0)
				.timeout(100000)
				.requestBody(PARAMSQUERY
						.concat(producto)
						.concat(CLICKANALYTICSTRUE));
	}
	
	/**
	 * Devuelve una cadena con la URL de la solicitud realizada 
	 * al sitio web donde se consulta el producto.
	 * 
	 * @param resDto
	 * @return  String
	 */
	public String getUrlAll(final IFProcessPrice resDto) {
		
		String productoAux = StringUtils.EMPTY;
		
		if(!StringUtils.isAllEmpty(resDto.getNomProducto())) {
			productoAux= resDto.getNomProducto()
				.replace(StringUtils.SPACE, SEPARADORURL);
		}
		
		return URLALLPRODUCTS.concat(productoAux);
	}
}
