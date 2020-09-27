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
import com.searchitemsapp.business.enterprises.Enterprise;
import com.searchitemsapp.business.enterprises.factory.EnterpriseFactory;
import com.searchitemsapp.dto.UrlDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DocumentsImpl implements Documents {
	
	private EnterpriseFactory enterpriseFactory;
	private DynamicWebProcessingImpl dynamicWebProcessingImpl;
	
	@Override
	public List<String> urlsPaginacion(final Document document, 
			final UrlDto urlDto, final int enterpriseId) 
					throws MalformedURLException {
		
		List<String> listUrlsResultado = Lists.newArrayList();
		
		listUrlsResultado.addAll(enterpriseFactory
				.getInstance(enterpriseId)
				.getListaUrls(document, urlDto));
		
		return listUrlsResultado;
	}
	
	@Override
	public List<Document> getHtmlDocument(final UrlDto urlDto, final String product) 
					throws IOException, URISyntaxException, 
					InterruptedException, JSONException {

    	List<Document> listDocuments = Lists.newArrayList();    	
		Enterprise enterprise = enterpriseFactory.getInstance(urlDto.getDidEmpresa());			
    	
    	Document document = getDocument(urlDto.getNomUrl(), enterprise.get_DID(), product);

    	List<String> liUrlsPorEmpresaPaginacion = urlsPaginacion(document, urlDto, enterprise.get_DID());
   		 			
   		if(liUrlsPorEmpresaPaginacion.isEmpty()) {
     			listDocuments.add(document);
   		} else {
	  		for (String url : liUrlsPorEmpresaPaginacion) {
	   			listDocuments.add(getDocument(url, enterprise.get_DID(), product));
			}
   		}
	
		return listDocuments;
	}
	
	private Document getDocument(String externalProductURL, 
			int enterpriseId, String requestProductName) 
					throws InterruptedException, URISyntaxException, IOException, JSONException {
	
		Enterprise enterprise = enterpriseFactory.getInstance(enterpriseId);

		if(enterprise.isDynamic()) {
			
			String dynamicContent = dynamicWebProcessingImpl.getDynamicHtmlContent(externalProductURL, enterpriseId);
			String externalProductoURI = new URL(externalProductURL).toURI().toString();
			
			return Jsoup.parse(dynamicContent, externalProductoURI);
		}
		
		Response httpResponse = enterprise.getJsoupConnection(externalProductURL, requestProductName).execute();
		return enterprise.getJsoupDocument(httpResponse, externalProductURL);
	}
}
