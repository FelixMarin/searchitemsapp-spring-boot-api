package com.searchitemsapp.processdata.empresas;

import org.jsoup.nodes.Element;

public interface ProcessDataCondis extends ProcessDataEmpresas {

	abstract int get_DID();
	abstract String tratarTagScript(final Element elem, final String cssSelector);
	abstract String eliminarTildesProducto(final String producto);
	abstract String reemplazarCaracteres(final String producto);
	
}
