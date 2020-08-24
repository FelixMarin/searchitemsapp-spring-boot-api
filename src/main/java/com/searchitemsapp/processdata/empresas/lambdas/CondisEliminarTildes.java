package com.searchitemsapp.processdata.empresas.lambdas;

@FunctionalInterface
public interface CondisEliminarTildes {
	abstract String eliminarTildesProducto(final String producto);
}
