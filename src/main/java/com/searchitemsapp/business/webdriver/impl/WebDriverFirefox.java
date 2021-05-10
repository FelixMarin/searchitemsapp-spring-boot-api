package com.searchitemsapp.business.webdriver.impl;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebDriverFirefox {
	
	private Environment environment;
	
	private Optional<WebDriver> webDriver;
	private Optional<File> firefoxExecutable;
	
	public WebDriverFirefox(Environment environment) {
		this.environment = environment;
		webDriver = Optional.empty();
	}
	
	public void setUp() {
		
		String driverPath;
		String executablePath;
		String os = System.getProperty("os.name");
		
		if(os.startsWith("Windows")) {		
			driverPath  = environment.getProperty("flow.value.firefox.driver.path");
			executablePath = environment.getProperty("folw.value.firefox.ejecutable.path");
			System.setProperty(environment.getProperty("flow.value.firefox.driver"), driverPath);
		} else {
			driverPath  = environment.getProperty("flow.value.firefox.driver.linux.path");
			executablePath = environment.getProperty("folw.value.firefox.ejecutable.linux.path");
			System.setProperty(environment.getProperty("flow.value.firefox.driver"), driverPath);			
		}
		
		FirefoxBinary binary = new FirefoxBinary(new File(executablePath));
		FirefoxOptions options = new FirefoxOptions();
		options.setBinary(binary);
		options.addArguments("-headless");
		options.addArguments("enable-automation");
		options.addArguments("-no-sandbox");
		options.addArguments("-disable-infobars");
		options.addArguments("-disable-dev-shm-usage");
		options.addArguments("-disable-browser-side-navigation");
		options.addArguments("-disable-gpu");
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		dc.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options.setBinary(binary));
		options.merge(dc);
		
		try {
			webDriver = Optional.ofNullable(new FirefoxDriver(options));
			
			webDriver.ifPresent(elem -> {
				elem.manage().window().maximize();
				elem.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
				elem.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);	
			});
		
		}catch(InvalidArgumentException e) {
			log.warn("Diver no inicializado", e);
		}
	}

	public Optional<WebDriver> getWebDriver() {
		return webDriver;
	}
	
	@PreDestroy
	public void shutdownWebDriver() {
		webDriver.ifPresent(elem -> elem.quit());
	}
	
	public void close() {
		webDriver.ifPresent(elem -> elem.close());
	}
	
	public boolean isPresent() {
		return webDriver.isPresent();
	}
	
	@PostConstruct
	public void checkFirefox() {
		String executablePath = environment.getProperty("folw.value.firefox.ejecutable.path");
		firefoxExecutable = Optional.ofNullable(new File(executablePath));
	}

	public Optional<File> firefoxExecutable() {
		return firefoxExecutable;
	}
}
