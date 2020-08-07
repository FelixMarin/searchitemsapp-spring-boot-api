package com.searchitemsapp.processdata;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.searchitemsapp.processdata.empresas.IFProcessDataConsum;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ProcessDataDynamic {
	
	private static final String SCROLLDOWN = "window.scrollTo(0, document.body.scrollHeight);";	

	@Autowired
	private IFProcessDataConsum processdataConsum;
	
	@Autowired
	private Environment env;
	
	public String getDynHtmlContent(final String strUrl, final int didEmpresa) throws InterruptedException {
		
		String strResultado;	
		int didConsum = Integer.parseInt(env.getProperty("flow.value.did.empresa.consum"));
		
		System.getProperties().setProperty(initDriver(0), 
				env.getProperty("flow.value.firefox.driver.path"));
		
		WebDriver webDriver = initWebDriver(0);
		cleanWindows(webDriver);
		
		if(didConsum == didEmpresa) {			
			strResultado = processdataConsum.getHtmlContent(webDriver, strUrl);
		} else {
			webDriver.navigate().to(strUrl);
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript(SCROLLDOWN);
			strResultado = webDriver.getPageSource();
		}
		
		cleanWindows(webDriver);
		 
		return strResultado;
	}
		
	private WebDriver initWebDriver(final int selector) {
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
					.usingDriverExecutable(new File(env.getProperty("flow.value.firefox.driver.path")))
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
			options.setBinary(env.getProperty("folw.value.firefox.ejecutable.path"));
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
        Set<String> windows = webDriver.getWindowHandles();
        Iterator<String> iter = windows.iterator();
        String[] winNames=new String[windows.size()];
        int i=0;
        while (iter.hasNext()) {
            winNames[i]=iter.next();
            i++;
        }

        if(winNames.length > 1) {
            for(i = winNames.length; i > 1; i--) {
            	webDriver.switchTo().window(winNames[i - 1]);
            	webDriver.close();
            }
        }
        webDriver.switchTo().window(winNames[0]);
    }

	private String initDriver(final int selector) {
		if(selector == 1) {
			return env.getProperty("flow.value.chrome.driver");
		} else {
			return env.getProperty("flow.value.firefox.driver");
		}
	}
}