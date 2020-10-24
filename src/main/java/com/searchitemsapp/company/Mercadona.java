package com.searchitemsapp.company;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.resource.Constants;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class Mercadona implements Company {

	private Environment environment;

	@Override
	public List<String> getUrls(final Document document, final UrlDto urlDto) throws MalformedURLException {

		String urlBase = urlDto.getNomUrl();

		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);

		return listaUrls;
	}

	public Long getId() {
		return NumberUtils.toLong(environment.getProperty("flow.value.did.empresa.mercadona"));
	}

	@Override
	public Connection getJsoupConnection(String externalProductURL, String requestProductName) {

		return Jsoup.connect(externalProductURL).userAgent(Constants.USER_AGENT.getValue())
				.method(Connection.Method.POST).referrer(environment.getProperty("flow.value.url.mercadona")).ignoreContentType(Boolean.TRUE)
				.header(Constants.ACCEPT_LANGUAGE.getValue(), Constants.ES_ES.getValue())
				.header(Constants.ACCEPT_ENCODING.getValue(), Constants.GZIP_DEFLATE_SDCH.getValue())
				.header(Constants.ACCEPT.getValue(), Constants.APPLICATION_JSON.getValue()).maxBodySize(0).timeout(100000)
				.requestBody("{\"params\":\"query=".concat(requestProductName).concat("&clickAnalytics=true\"}"));

	}

	@Override
	public Document getJsoupDocument(@NonNull Response httpResponse, 
			String externalProductURL) throws IOException {
			 
		Document responseDocument = null;
		
		try {
			JSONObject jsonDocumentBody = new JSONObject(httpResponse.body()); 
			String xmlDocumentBody = XML.toString(jsonDocumentBody);
			
			if (xmlDocumentBody.isBlank()) {
				return Document.createShell(xmlDocumentBody);
			}
	
			xmlDocumentBody = xmlDocumentBody.replace(Constants.DOT.getValue(), Constants.COMMA.getValue());	
			xmlDocumentBody = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>".concat("<root>").concat(xmlDocumentBody).concat("</root>");
			xmlDocumentBody = xmlDocumentBody.replace("&lt;em&gt;", StringUtils.EMPTY);
			xmlDocumentBody = xmlDocumentBody.replace("&lt;/em&gt;", StringUtils.EMPTY);
	
			responseDocument = Jsoup.parse(xmlDocumentBody, StringUtils.EMPTY, Parser.xmlParser());
			responseDocument.setBaseUri(externalProductURL);
		
		} catch(org.json.JSONException e) {
			log.warn("Can't process body " + Thread.currentThread().getStackTrace()[1].toString());
		}

		return responseDocument;
	}
	
	@Override
	public String getAllUrlsToSearch(ProductDto productDto) {
		String productoAux= productDto.getNomProducto().replace(StringUtils.SPACE, Constants.SPACE_URL.getValue());		
		productDto.setImagen(productDto.getImagen().replace(Constants.COMMA.getValue(), Constants.DOT.getValue()));
		Optional<String> urlAll = Optional.ofNullable(environment.getProperty("flow.value.url.all"));
		urlAll.ifPresent(elem -> elem.concat(productoAux));
		return urlAll.orElse(productoAux);
	}
	
	@Override
	public String selectorTextExtractor(Element documentElement,
			List<String> cssSelectorList, String cssSelector) {
		
		return documentElement.selectFirst(cssSelector).text();
	}
}