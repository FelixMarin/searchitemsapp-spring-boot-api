package com.searchitemsapp.business.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
	private WebDriverManager webDriverManager;
	
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
	public List<Document> getHtmlDocument(final UrlDto urlDto, final String product) 
					throws IOException, URISyntaxException, 
					InterruptedException, JSONException {

    	List<Document> listDocuments = Lists.newArrayList(); 
		Company company = companiesGroup.getInstance(urlDto.getDidEmpresa());		 	
    	
    	Document document = getDocument(urlDto.getNomUrl(), company.getId(), product);

    	List<String> liUrlsPorEmpresaPaginacion = urlsPaginacion(document, urlDto, company.getId());
   		
    	if(liUrlsPorEmpresaPaginacion.isEmpty()) {
     			listDocuments.add(document);
   		} else {
	  		for (String url : liUrlsPorEmpresaPaginacion) {
	   			listDocuments.add(getDocument(url, company.getId(), product));
			}
   		}
	
		return listDocuments;
	}
	
	private Document getDocument(String externalProductURL, 
			Long companyId, String requestProductName) 
					throws InterruptedException, URISyntaxException, IOException {
	
		Company company = companiesGroup.getInstance(companyId);

		if(company.isDynamic()) {
			
			String dynamicContent = webDriverManager.getDynamicHtmlContent(externalProductURL, companyId);
			String externalProductoURI = new URL(externalProductURL).toURI().toString();
			return Jsoup.parse(dynamicContent, externalProductoURI);
		}
		
		Response httpResponse = company.getJsoupConnection(externalProductURL, requestProductName).execute();
		return company.getJsoupDocument(httpResponse, externalProductURL);
	}
}
