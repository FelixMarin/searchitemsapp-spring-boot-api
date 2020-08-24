package com.searchitemsapp.processdata.empresas.lambdas;

import org.jsoup.Connection;

@FunctionalInterface
public interface MercadonaGetConnection {
	abstract Connection getConnection(final String strUrl, final String producto);
}
