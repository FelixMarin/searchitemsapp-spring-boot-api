package com.searchitemsapp.business;

public interface DynamicWebManager {
	
	abstract String getDynHtmlContent(final String strUrl, final int didEmpresa) throws InterruptedException;
	
}
