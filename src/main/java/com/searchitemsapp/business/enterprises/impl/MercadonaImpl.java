package com.searchitemsapp.business.enterprises.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.enterprises.Company;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.resources.Constants;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MercadonaImpl implements Company {

	private static final Logger LOGGER = LoggerFactory.getLogger(MercadonaImpl.class);  
	
	private Environment environment;

	@Override
	public List<String> getUrls(final Document document, final UrlDto urlDto) throws MalformedURLException {

		String urlBase = urlDto.getNomUrl();

		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);

		return listaUrls;
	}

	public int get_DID() {
		return NumberUtils.toInt(environment.getProperty("flow.value.did.empresa.mercadona"));
	}

	@Override
	public Connection getJsoupConnection(String externalProductURL, String requestProductName) {

		return Jsoup.connect(externalProductURL).userAgent(Constants.USER_AGENT.getValue())
				.method(Connection.Method.POST).referrer("https://tienda.mercadona.es/").ignoreContentType(Boolean.TRUE)
				.header("Accept-Language", "es-ES,es;q=0.8").header("Accept-Encoding", "gzip, deflate, sdch")
				.header("Accept", "application/json").maxBodySize(0).timeout(100000)
				.requestBody("{\"params\":\"query=".concat(requestProductName).concat("&clickAnalytics=true\"}"));

	}

	@Override
	public Document getJsoupDocument(Response httpResponse, String externalProductURL) throws IOException {
		
		if (StringUtils.isAllEmpty(httpResponse.body())) {
			return new Document(StringUtils.EMPTY);
		}
		
		Document responseDocument = null;
		
		try {
			JSONObject jsonDocumentBody = new JSONObject(httpResponse.body());

			String xmlDocumentBody = XML.toString(jsonDocumentBody);
			
			if (StringUtils.isAllEmpty(xmlDocumentBody)) {
				return new Document(StringUtils.EMPTY);
			}
	
			xmlDocumentBody = xmlDocumentBody.replace(Constants.DOT.getValue(), Constants.COMMA.getValue());	
			xmlDocumentBody = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>".concat("<root>").concat(xmlDocumentBody).concat("</root>");
			xmlDocumentBody = xmlDocumentBody.replace("&lt;em&gt;", StringUtils.EMPTY);
			xmlDocumentBody = xmlDocumentBody.replace("&lt;/em&gt;", StringUtils.EMPTY);
	
			responseDocument = Jsoup.parse(xmlDocumentBody, StringUtils.EMPTY, Parser.xmlParser());
			responseDocument.setBaseUri(externalProductURL);
		
		} catch(org.json.JSONException e) {
			LOGGER.warn("Can't process body " + Thread.currentThread().getStackTrace()[1].toString());
		}

		return responseDocument;
	}
	
	@Override
	public String getAllUrlsToSearch(ProductDto productDto) {
		
		String productoAux= productDto.getNomProducto().replace(StringUtils.SPACE, "%20");		
		productDto.setImagen(productDto.getImagen().replace(Constants.COMMA.getValue(), Constants.DOT.getValue()));
		
		return environment.getProperty("flow.value.url.all").concat(productoAux);
	}
	
	@Override
	public String selectorTextExtractor(Element documentElement,
			List<String> cssSelectorList, String cssSelector) {
		return documentElement.selectFirst(cssSelector).text();
	}
}