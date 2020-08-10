package com.searchitemsapp.processdata.empresas;

public interface ProcessDataEmpresasFactory {

	abstract ProcessDataEmpresas getEnterpriseData(final int idEmpresa);
}
