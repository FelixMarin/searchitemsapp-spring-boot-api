package com.searchitemsapp.business.webdriver.impl;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
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
	
	public boolean isPresent() {
		return webDriverFirefox.isPresent();
	}
	
	public void closeDriver() {
		webDriverFirefox.close();
	}
	
	public Optional<File> firefoxExecutable() {
		return webDriverFirefox.firefoxExecutable();
	}
	
	public void openWindow(WebDriver elem) {
		JavascriptExecutor jse = (JavascriptExecutor)elem;
		jse.executeScript("window.open('about:blank','_blank');");
		List<String> tabs = Lists.newArrayList(elem.getWindowHandles());
		elem.switchTo().window(tabs.get(elem.getWindowHandles().size()-1));
	}
}