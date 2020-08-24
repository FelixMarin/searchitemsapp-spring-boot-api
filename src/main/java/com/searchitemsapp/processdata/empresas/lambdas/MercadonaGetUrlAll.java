package com.searchitemsapp.processdata.empresas.lambdas;

import com.searchitemsapp.processdata.ProcessPrice;

@FunctionalInterface
public interface MercadonaGetUrlAll {
	abstract String getUrlAll(final ProcessPrice resDto);
}
