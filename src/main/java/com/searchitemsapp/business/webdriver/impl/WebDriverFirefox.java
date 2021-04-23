package com.searchitemsapp.business.webdriver.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.apache.commons.lang3.ObjectUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class WebDriverFirefox {
	
	private Environment environment;
	
	private WebDriver webDriver;
	
	public WebDriverFirefox(Environment environment) {
		this.environment = environment;
	}
	
	public void setUp() {
		
		String driverPath  = environment.getProperty("flow.value.firefox.driver.path");
		String executablePath = environment.getProperty("folw.value.firefox.ejecutable.path");
		System.setProperty(environment.getProperty("flow.value.firefox.driver"), driverPath);
		
		FirefoxOptions options = new FirefoxOptions();
		options.setBinary(executablePath);
		options.addArguments("-headless");
		options.addArguments("enable-automation");
		options.addArguments("-no-sandbox");
		options.addArguments("-disable-infobars");
		options.addArguments("-disable-dev-shm-usage");
		options.addArguments("-disable-browser-side-navigation");
		options.addArguments("-disable-gpu");
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		dc.setCapability("moz:firefoxOptions", options);
		options.merge(dc);
		webDriver = new FirefoxDriver(options);
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);	
	}

	public Optional<WebDriver> getWebDriver() {
		return Optional.ofNullable(webDriver);
	}
	
	@PreDestroy
	public void shutdownWebDriver() {
		webDriver.quit();
	}
	
	public void close() {
		webDriver.close();
	}
	
	public boolean isOpen() {
		return ObjectUtils.allNotNull(webDriver);
	}
}
