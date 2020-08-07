package com.searchitemsapp.processdata.empresas;

public interface IFProcessDataSimply extends IFProcessDataEmpresas {
	
	abstract int get_DID();
	abstract String reemplazarCaracteres(final String producto);
}
