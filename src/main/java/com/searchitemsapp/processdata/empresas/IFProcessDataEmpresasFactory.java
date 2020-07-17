package com.searchitemsapp.processdata.empresas;

public interface IFProcessDataEmpresasFactory {

	abstract IFProcessDataEmpresas getScrapingEmpresa(final int idEmpresa);
}
