package com.searchitemsapp.business.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.searchitemsapp.business.Documents;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.UrlDto;	

@SpringBootTest
class DocumentsImplTest {
	
	@Autowired
	Documents document;
	
	@Test
	void testUrlsPaginacion() throws MalformedURLException {
		Document jsoupDoc = Document.createShell("");
		UrlDto urlDto = UrlDto.builder().nomUrl("https://www.dia.es/compra-online/search?q=miel%3Aprice-asc&page=0&disp=").build();
		List<String> listUrls = document.urlsPaginacion(jsoupDoc, urlDto, 101l);
		assertFalse(listUrls.isEmpty());
	}
	
	@Test
	void testGetHtmlDocument() throws IOException, URISyntaxException, InterruptedException, JSONException {
		CssSelectorsDto selectores = CssSelectorsDto.builder().selPaginacion("0|0").build();
		
		UrlDto urlDto = UrlDto.builder()
				.nomUrl("https://www.dia.es/compra-online/search?q=miel%3Aprice-asc&page=0&disp=")
				.didEmpresa(105l).selectores(selectores).build();
		List<Document> listDocuments = document.getHtmlDocument(urlDto, "miel");
		assertFalse(listDocuments.isEmpty());
	}
}
