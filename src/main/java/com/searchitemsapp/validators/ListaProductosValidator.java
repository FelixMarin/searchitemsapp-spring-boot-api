package com.searchitemsapp.validators;

public interface ListaProductosValidator {
	abstract boolean isParams(String[] args);
	abstract boolean isParamsLiveSearch(String arg);
}
