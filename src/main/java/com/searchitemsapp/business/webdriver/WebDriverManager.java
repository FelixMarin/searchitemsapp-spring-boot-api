package com.searchitemsapp.business.webdriver;

import java.util.Optional;

import org.openqa.selenium.WebDriver;

public interface WebDriverManager {

	abstract String getDynamicHtmlContentSync(WebDriver webDriver, String externalProductURL, Long companyId) throws InterruptedException;
	abstract String getDynamicHtmlContentAsync(WebDriver webDriver, String externalProductURL, Long companyId) throws InterruptedException;
	
	abstract Optional<WebDriver>  getWebDriver();
	abstract void shutdownWebDriver();
	abstract boolean isOpen();
	abstract void setUp();
	abstract void closeDriver();
}

