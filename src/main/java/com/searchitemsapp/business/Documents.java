package com.searchitemsapp.business;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.jsoup.nodes.Document;

import com.searchitemsapp.business.webdriver.WebDriverManager;
import com.searchitemsapp.dto.UrlDto;

public interface Documents {

	abstract List<Document> getHtmlDocument(final UrlDto urlDto, final String productName,WebDriverManager webDriverManager) 
					throws IOException, URISyntaxException, 
					InterruptedException, JSONException;
}
