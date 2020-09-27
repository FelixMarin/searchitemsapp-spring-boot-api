package com.searchitemsapp.business.impl;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.searchitemsapp.business.DynamicWebProcessing;
import com.searchitemsapp.business.enterprises.Enterprise;
import com.searchitemsapp.business.enterprises.factory.EnterpriseFactory;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DynamicWebProcessingImpl implements DynamicWebProcessing {
	
	private Environment environment;
	private EnterpriseFactory enterpriseFactory;
	
	@Override
	public String getDynamicHtmlContent(String externalProductURL, int enterpriseId) throws InterruptedException {
		
		String pageSource;	
		Enterprise enterprise = enterpriseFactory.getInstance(enterpriseId);
		
		System.getProperties().setProperty(getADriver(0), 
				environment.getProperty("flow.value.firefox.driver.path"));
		
		WebDriver webDriver = initWebDriver(0);
		cleanWindows(webDriver);
		pageSource = enterprise.getHtmlContent(webDriver, externalProductURL);			
		cleanWindows(webDriver);
		 
		return pageSource;
	}
		
	private WebDriver initWebDriver(int selector) {
		if(selector == 1) {
			return setupWebDriverChrome();
		} else {
			return setupWebDriverFirefox();
		}
	}

	private WebDriver setupWebDriverChrome() {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("--incognito");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			DesiredCapabilities dc = DesiredCapabilities.chrome();
			ChromeDriverService chromeService = new ChromeDriverService.Builder()
					.usingDriverExecutable(new File(environment.getProperty("flow.value.firefox.driver.path")))
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

	private WebDriver setupWebDriverFirefox() {
		
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
        
        String firstNameWindow = windows.stream().findFirst().get();
                
        windows.stream().filter(elem -> !elem.equals(firstNameWindow)).forEach(elem -> {
        	webDriver.switchTo().window(elem);
        	webDriver.close(); 
        });
        
        webDriver.switchTo().window(firstNameWindow);
    }

	private String getADriver(int selector) {
		if(selector == 1) {
			return environment.getProperty("flow.value.chrome.driver");
		} else {
			return environment.getProperty("flow.value.firefox.driver");
		}
	}
}