package com.searchitemsapp.processdata.empresas;

public interface ProcessDataEroski extends ProcessDataEmpresas {
	
	abstract int get_DID();
	abstract String reemplazarCaracteres(final String producto);
}
