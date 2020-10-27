package com.searchitemsapp.business.webdriver;

import org.openqa.selenium.WebDriver;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.searchitemsapp.company.Company;
import com.searchitemsapp.company.factory.CompaniesGroup;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class WebDriverManager {
	
	private Environment environment;
	private CompaniesGroup companiesGroup;
	private WebDriverFirefox webDriverFirefox;

	public String getDynamicHtmlContent(String externalProductURL, Long companyId) 
			throws InterruptedException {
		
		Company company = companiesGroup.getInstance(companyId);
		String driverPath  = environment.getProperty("flow.value.firefox.driver.path");
		String executablePath = environment.getProperty("folw.value.firefox.ejecutable.path");
		WebDriver webDriver = webDriverFirefox.setup(driverPath, executablePath);
		return company.getHtmlContent(webDriver, externalProductURL);			
	}
}