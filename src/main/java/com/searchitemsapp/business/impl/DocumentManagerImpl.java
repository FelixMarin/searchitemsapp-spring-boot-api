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
import com.searchitemsapp.business.DocumentManager;
import com.searchitemsapp.business.enterprises.Enterprise;
import com.searchitemsapp.business.enterprises.factory.EnterpriseFactory;
import com.searchitemsapp.dto.UrlDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DocumentManagerImpl implements DocumentManager {
	
	private EnterpriseFactory enterpriseFactory;
	private DynamicWebManagerImpl procesDataDynamic;
	
	public List<Document> getHtmlDocument(final UrlDto urlDto, final String product) 
					throws IOException, URISyntaxException, 
					InterruptedException, JSONException {

    	List<Document> listDocuments = Lists.newArrayList();    	
		Enterprise enterprise = enterpriseFactory.getEnterpriseData(urlDto.getDidEmpresa());			
    	
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
	
		Enterprise enterprise = enterpriseFactory.getEnterpriseData(enterpriseId);

		if(enterprise.isDynamic()) {
			
			String dynamicContent = procesDataDynamic.getDynHtmlContent(externalProductURL, enterpriseId);
			String externalProductoURI = new URL(externalProductURL).toURI().toString();
			
			return Jsoup.parse(dynamicContent, externalProductoURI);
		}
		
		Response response = enterprise.getJsoupConnection(externalProductURL, requestProductName).execute();
		return enterprise.getJsoupDocument(response, externalProductURL);
	}
	
	public List<String> urlsPaginacion(final Document document, 
			final UrlDto urlDto, final int idEmpresa) 
					throws MalformedURLException {
		
		List<String> listUrlsResultado = Lists.newArrayList();
		
		listUrlsResultado.addAll(enterpriseFactory
				.getEnterpriseData(idEmpresa).getListaUrls(document, urlDto));
		
		return listUrlsResultado;
	}
}
