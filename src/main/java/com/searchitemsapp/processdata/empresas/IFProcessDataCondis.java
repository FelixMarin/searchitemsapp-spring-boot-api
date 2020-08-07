package com.searchitemsapp.processdata.empresas;

import org.jsoup.nodes.Element;

public interface IFProcessDataCondis extends IFProcessDataEmpresas {

	abstract int get_DID();
	abstract String tratarTagScript(final Element elem, final String cssSelector);
	abstract String eliminarTildesProducto(final String producto);
	abstract String reemplazarCaracteres(final String producto);
	
}
