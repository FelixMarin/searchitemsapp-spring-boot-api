package com.searchitemsapp.business.enterprises;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.resources.Constants;

/**
 * @author Felix Marin Ramirez
 *
 */
public interface Company {


	abstract List<String> getUrls(final Document document, final UrlDto urlDto) throws MalformedURLException;
	
	default Connection getJsoupConnection(String externalProductURL, String requestProductName) throws MalformedURLException {
		
		URL url = new URL(externalProductURL);
		
		return Jsoup.connect(externalProductURL)
				.userAgent(Constants.USER_AGENT.getValue())
				.method(Connection.Method.GET)
				.referrer(url.getProtocol().concat(Constants.PROTOCOL_ACCESSOR.getValue()).concat(url.getHost().concat(Constants.SLASH.getValue())))
				.ignoreContentType(Boolean.TRUE)
				.header(Constants.ACCEPT_LANGUAGE.getValue(), Constants.ES_ES.getValue())
				.header(Constants.ACCEPT_ENCODING.getValue(), Constants.GZIP_DEFLATE_SDCH.getValue())
				.header(Constants.ACCEPT.getValue(), Constants.TEXT_HTML_APPLICATION.getValue())
				.maxBodySize(0)
				.timeout(100000);
	}
	
	default Document getJsoupDocument(Response httpResponse, String externalProductURL) throws IOException {
		return httpResponse.parse();
	}
	
	default boolean isDynamic() {
		return false;
	}
	
	abstract int get_DID();
	
	default String getHtmlContent(final WebDriver webDriver, final String strUrl) 
			throws InterruptedException, ElementNotVisibleException, 
				TimeoutException, ElementClickInterceptedException {
		
		webDriver.navigate().to(strUrl);
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		return webDriver.getPageSource();
	}
	
	default String removeInitialBrand(String productName) {
		return productName;
	}
	
	default String reemplazarCaracteres(String producto) {
		return producto;
	}
	
	default String getAllUrlsToSearch(ProductDto productDto) {
		return productDto.getNomUrlAllProducts();
	}
	
	default String selectorTextExtractor(Element documentElement,
			List<String> cssSelectorList, String cssSelector) {
		
		int selectorsListSize = cssSelectorList.size();
		
		if(selectorsListSize == 1) {
			return documentElement.select(cssSelectorList.get(0)).text();
		} else if(selectorsListSize == 2) {
			return documentElement.select(cssSelectorList.get(0)).attr(cssSelectorList.get(1));
		} else {
			return documentElement.select(cssSelector).text();
		}
	}

	
}
