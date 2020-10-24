package com.searchitemsapp.business;

import org.openqa.selenium.WebDriver;

public interface DynamicWebProcessing {
	
	abstract String getDynamicHtmlContent(final String strUrl, final Long companyId, Long driverId) throws InterruptedException;
	abstract WebDriver initWebDriver(Long selector);
}
