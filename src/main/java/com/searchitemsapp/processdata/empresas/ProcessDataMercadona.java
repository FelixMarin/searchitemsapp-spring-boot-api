package com.searchitemsapp.processdata.empresas;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.searchitemsapp.processdata.ProcessPrice;

public interface ProcessDataMercadona extends ProcessDataEmpresas {
	
	abstract int get_DID();
	abstract String getResult(final Element elem, final String cssSelector);
	abstract Document getDocument(final String url, final String body);
	abstract Connection getConnection(final String strUrl, final String producto);
	abstract String getUrlAll(final ProcessPrice resDto);
}
