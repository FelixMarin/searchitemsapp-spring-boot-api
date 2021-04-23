package com.searchitemsapp.business.webdriver.impl;

import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import com.searchitemsapp.business.webdriver.WebDriverManager;
import com.searchitemsapp.company.factory.CompaniesGroup;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class WebDriverManagerImpl implements WebDriverManager {
	
	private CompaniesGroup companiesGroup;
	private WebDriverFirefox webDriverFirefox;

	public synchronized String getDynamicHtmlContentSync(WebDriver webDriver, String externalProductURL, Long companyId) 
			throws InterruptedException {
		
		return companiesGroup.getInstance(companyId).getHtmlContent(webDriver, externalProductURL);			
	}
	
	public String getDynamicHtmlContentAsync(WebDriver webDriver, String externalProductURL, Long companyId) 
			throws InterruptedException {
		
		return companiesGroup.getInstance(companyId).getHtmlContent(webDriver, externalProductURL);			
	}
	
	public void setUp() {
		webDriverFirefox.setUp();
	}
	
	public Optional<WebDriver> getWebDriver() {		
		return webDriverFirefox.getWebDriver();
	}
	
	public void shutdownWebDriver() {
		webDriverFirefox.shutdownWebDriver();
	}
	
	public boolean isOpen() {
		return webDriverFirefox.isOpen();
	}
	
	public void closeDriver() {
		webDriverFirefox.close();
	}
}