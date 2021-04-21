package com.searchitemsapp.business.webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class WebDriverFirefox {
	
	private Environment environment; 
	
	public WebDriver setup() {
		
		String driverPath  = environment.getProperty("flow.value.firefox.driver.path");
		String executablePath = environment.getProperty("folw.value.firefox.ejecutable.path");
		System.setProperty(environment.getProperty("flow.value.firefox.driver"), driverPath);
		
		FirefoxOptions options = new FirefoxOptions();
		options.setBinary(executablePath);
		options.addArguments("-headless");
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		dc.setCapability("moz:firefoxOptions", options);
		options.merge(dc);
		WebDriver driver = new FirefoxDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);	
		
		return driver;
	}
}
