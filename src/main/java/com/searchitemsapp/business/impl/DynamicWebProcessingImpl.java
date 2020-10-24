package com.searchitemsapp.business.impl;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.searchitemsapp.business.DynamicWebProcessing;
import com.searchitemsapp.company.Company;
import com.searchitemsapp.company.factory.CompaniesGroup;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DynamicWebProcessingImpl implements DynamicWebProcessing {
	
	private Environment environment;
	private CompaniesGroup companiesGroup;
	
	@Override
	public String getDynamicHtmlContent(String externalProductURL, 
			Long companyId, Long driverId) throws InterruptedException {
		
		WebDriver webDriver = initWebDriver(driverId);
		cleanWindows(webDriver);
		Company company = companiesGroup.getInstance(companyId);
		String pageSource = company.getHtmlContent(webDriver, externalProductURL);			
		cleanWindows(webDriver);
		 
		return pageSource;
	}
		
	public WebDriver initWebDriver(Long selector) {
		if(selector == 1) {
			return setupWebDriverChrome();
		} else if(selector == 2) {
			return setupWebDriverEdge();
		} else {
			return setupWebDriverFirefox();
		}
	}

	private WebDriver setupWebDriverChrome() {
		
		String driverPath = environment.getProperty("flow.value.google.driver.path");
		System.setProperty("webdriver.chrome.driver",driverPath);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("--incognito");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			DesiredCapabilities dc = DesiredCapabilities.chrome();
			ChromeDriverService chromeService = new ChromeDriverService.Builder()
					.usingDriverExecutable(new File(driverPath))
                    .usingAnyFreePort()
                    .build();
			dc.setCapability(ChromeOptions.CAPABILITY, options);
			options.merge(dc);
			WebDriver webDriver = new ChromeDriver(chromeService, options);
			webDriver.manage().window().maximize();
			webDriver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
			webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
			
			return webDriver;
	}

	private WebDriver setupWebDriverEdge() {
		
		String driverPath = environment.getProperty("flow.value.edge.driver.path");
        System.setProperty("webdriver.edge.driver", driverPath);
        EdgeOptions op=new EdgeOptions();
        return new EdgeDriver(op);
	}
	
	private WebDriver setupWebDriverFirefox() {
		
		String driverPath  = environment.getProperty("flow.value.firefox.driver.path");
		System.setProperty("webdriver.gecko.driver", driverPath);
		
		FirefoxOptions options = new FirefoxOptions();
		options.setBinary(environment.getProperty("folw.value.firefox.ejecutable.path"));
		options.addArguments("-headless");
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		dc.setCapability("moz:firefoxOptions", options);
		options.merge(dc);
		WebDriver webDriver = new FirefoxDriver(options);
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);	
		
		return webDriver;
	}

	private void cleanWindows(WebDriver webDriver) {
		
		List<String> windows = webDriver.getWindowHandles()
				.stream()
				.sorted(Collections.reverseOrder())
				.collect(Collectors.toList());
        
        String firstNameWindow = windows.stream().findFirst().orElse(StringUtils.EMPTY);
                
        windows.stream().filter(elem -> !elem.equals(firstNameWindow)).forEach(elem -> {
        	webDriver.switchTo().window(elem);
        	webDriver.close(); 
        });
        
        webDriver.switchTo().window(firstNameWindow);
    }
}