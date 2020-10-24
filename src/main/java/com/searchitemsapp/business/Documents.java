package com.searchitemsapp.business;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.jsoup.nodes.Document;

import com.searchitemsapp.dto.UrlDto;

public interface Documents {

	abstract List<Document> getHtmlDocument(final UrlDto urlDto, final String productName) 
					throws IOException, URISyntaxException, 
					InterruptedException, JSONException;
	
	abstract List<String> urlsPaginacion(final Document document, 
			final UrlDto urlDto, final Long companyId) 
					throws MalformedURLException;
}
