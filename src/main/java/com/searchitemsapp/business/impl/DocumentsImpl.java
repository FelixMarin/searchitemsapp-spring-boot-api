package com.searchitemsapp.business.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.codehaus.jettison.json.JSONException;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.Documents;
import com.searchitemsapp.business.webdriver.WebDriverManager;
import com.searchitemsapp.company.Company;
import com.searchitemsapp.company.factory.CompaniesGroup;
import com.searchitemsapp.dto.UrlDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DocumentsImpl implements Documents {
	
	private CompaniesGroup companiesGroup;
	
	@Override
	public List<String> urlsPaginacion(Document document, UrlDto urlDto, Long companyId) 
					throws MalformedURLException {
		
		List<String> listUrlsResultado = Lists.newArrayList();
		
		listUrlsResultado.addAll(companiesGroup
				.getInstance(companyId)
				.getUrls(document, urlDto));
		
		return listUrlsResultado;
	}
	
	@Override
	public synchronized List<Document> getHtmlDocument(final UrlDto urlDto, final String product, WebDriverManager webDriverManager) 
					throws IOException, URISyntaxException, 
					InterruptedException, JSONException {

    	List<Document> listDocuments = Lists.newArrayList(); 
		Company company = companiesGroup.getInstance(urlDto.getDidEmpresa()); 
		Optional<WebDriver> opWebDriver = Optional.empty();
		Document document;

		if(company.isDynamic()) {	
			
			webDriverManager.firefoxExecutable().ifPresent(elem -> {
				
				if(webDriverManager.isPresent()) {
					webDriverManager.shutdownWebDriver();
				}
				
				webDriverManager.setUp();
				
			});
			
			opWebDriver = webDriverManager.getWebDriver();			
			opWebDriver.ifPresent(elem -> webDriverManager.openWindow(elem));
			
			document = getDocumentSync(urlDto.getNomUrl(), company, product, opWebDriver, webDriverManager);
		} else {
			document = getDocumentAsync(urlDto.getNomUrl(), company, product);
		}

    	List<String> liUrlsPorEmpresaPaginacion = urlsPaginacion(document, urlDto, company.getId());
   		
    	if(liUrlsPorEmpresaPaginacion.size() < 2) {
     			listDocuments.add(document);
   		} else {
	  		for (String url : liUrlsPorEmpresaPaginacion) {
	   			listDocuments.add(getDocumentSync(url, company, product, opWebDriver, webDriverManager));
			}
   		}
		return listDocuments;
	}
	
	private synchronized  Document getDocumentSync(String externalProductURL, 
			Company company, String requestProductName, Optional<WebDriver> opWebDriver, WebDriverManager webDriverManager) 
					throws InterruptedException, URISyntaxException, IOException {
	
		if(company.isDynamic() && opWebDriver.isPresent()) {
			String dynamicContent = webDriverManager.getDynamicHtmlContentSync(opWebDriver.get(),externalProductURL, company.getId());
			String externalProductoURI = new URL(externalProductURL).toURI().toString();
			return Jsoup.parse(dynamicContent, externalProductoURI);
		}
		
		return getDocumentAsync(externalProductURL, company, requestProductName);
	}
	
	private Document getDocumentAsync(String externalProductURL, 
			Company company, String requestProductName) throws IOException {
		
		Response httpResponse = company.getJsoupConnection(externalProductURL, requestProductName).execute();
		return company.getJsoupDocument(httpResponse, externalProductURL);
	}
}
