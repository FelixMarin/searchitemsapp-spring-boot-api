package com.searchitemsapp.processdata.empresas.lambdas;

import org.codehaus.jettison.json.JSONException;
import org.jsoup.nodes.Document;

@FunctionalInterface
public interface MercadonaGetDocument {
	abstract Document getDocument(final String url, final String body) throws JSONException;
}
