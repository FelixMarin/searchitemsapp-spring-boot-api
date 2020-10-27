package com.searchitemsapp.business.webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;

@Component
public class WebDriverFirefox {
	
	public WebDriver setup(String driverPath, String executablePath) {
		
		System.setProperty("webdriver.gecko.driver", driverPath);
		
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
