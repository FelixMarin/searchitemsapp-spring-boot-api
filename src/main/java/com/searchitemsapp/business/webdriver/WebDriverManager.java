package com.searchitemsapp.business.webdriver;

import java.io.File;
import java.util.Optional;

import org.openqa.selenium.WebDriver;

public interface WebDriverManager {

	abstract String getDynamicHtmlContentSync(WebDriver webDriver, String externalProductURL, Long companyId) throws InterruptedException;
	abstract String getDynamicHtmlContentAsync(WebDriver webDriver, String externalProductURL, Long companyId) throws InterruptedException;
	
	abstract Optional<WebDriver>  getWebDriver();
	abstract void shutdownWebDriver();
	abstract boolean isPresent();
	abstract void setUp();
	abstract void closeDriver();
	abstract Optional<File> firefoxExecutable();
	abstract void openWindow(WebDriver elem);
}

