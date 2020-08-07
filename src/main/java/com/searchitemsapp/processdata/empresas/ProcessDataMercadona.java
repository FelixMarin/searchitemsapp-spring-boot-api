package com.searchitemsapp.processdata.empresas;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDTO;
import com.searchitemsapp.processdata.IFProcessPrice;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ProcessDataMercadona implements IFProcessDataMercadona {
	
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
	
	@Autowired
	private Environment env;
	
	@Override
	public List<String> getListaUrls(final Document document, final UrlDTO urlDto)
			throws MalformedURLException {
			
		String urlBase = urlDto.getNomUrl();
		
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);
		
		return listaUrls;
	}	

	public Document getDocument(final String url, final String body) {
		
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
	
	public String getResult(final Element elem, final String cssSelector) {		
		return elem.selectFirst(cssSelector).text();
	}

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
	
	public String getUrlAll(final IFProcessPrice resDto) {
		
		String productoAux = StringUtils.EMPTY;
		
		if(!StringUtils.isAllEmpty(resDto.getNomProducto())) {
			productoAux= resDto.getNomProducto()
				.replace(StringUtils.SPACE, SEPARADORURL);
		}
		
		return URLALLPRODUCTS.concat(productoAux);
	}

	public int get_DID() {
		return NumberUtils.toInt(env.getProperty("flow.value.did.empresa.mercadona"));
	}
	
	
}