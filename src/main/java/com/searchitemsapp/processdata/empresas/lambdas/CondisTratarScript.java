package com.searchitemsapp.processdata.empresas.lambdas;

import org.jsoup.nodes.Element;

@FunctionalInterface
public interface CondisTratarScript {
	abstract String tratarTagScript(final Element elem, final String cssSelector);
}
