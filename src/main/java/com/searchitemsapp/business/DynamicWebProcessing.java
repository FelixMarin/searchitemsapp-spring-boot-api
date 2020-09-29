package com.searchitemsapp.business;

public interface DynamicWebProcessing {
	
	abstract String getDynamicHtmlContent(final String strUrl, final int companyId) throws InterruptedException;
	
}
