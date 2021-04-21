package com.searchitemsapp.business.webdriver;

import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import com.searchitemsapp.company.factory.CompaniesGroup;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class WebDriverManager {
	
	private CompaniesGroup companiesGroup;
	private WebDriverFirefox webDriverFirefox;

	public String getDynamicHtmlContent(WebDriver webDriver, String externalProductURL, Long companyId) 
			throws InterruptedException {
		
		return companiesGroup.getInstance(companyId).getHtmlContent(webDriver, externalProductURL);			
	}
	
	public WebDriver getWebDriver() {		
		return webDriverFirefox.setup();
	}
	
	public void webDriverQuit(Optional<WebDriver> webDriver) {
		webDriver.ifPresent(elem -> elem.quit());
	}
}