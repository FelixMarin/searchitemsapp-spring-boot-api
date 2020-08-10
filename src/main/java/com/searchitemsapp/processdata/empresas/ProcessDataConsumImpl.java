package com.searchitemsapp.processdata.empresas;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDTO;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ProcessDataConsumImpl implements ProcessDataConsum {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataConsumImpl.class);   

	private static final String SCROLL_INTO_VIEW = "arguments[0].scrollIntoView(true)";
	
	@Autowired
	private Environment env;

	@Override
	public List<String> getListaUrls(Document document, UrlDTO urlDto)
			throws MalformedURLException {

		String urlBase = urlDto.getNomUrl();
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);
		
		return listaUrls;
	}

	public String getHtmlContent(final WebDriver webDriver, final String strUrl) throws InterruptedException {
		
		WebElement wButton;
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		boolean isButton = Boolean.TRUE;
		
		webDriver.get(strUrl);		
		WebDriverWait wait = new WebDriverWait(webDriver, 30);
		
		try {
			
			do {				
				Thread.sleep(2000);
				wButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("grid__footer-viewMore")));
				if (Objects.isNull(wButton)) {
					isButton = Boolean.FALSE;
				} else {
					js.executeScript(SCROLL_INTO_VIEW, wButton);
					wButton.click();
				}
			} while (isButton);
			
		} catch (ElementNotVisibleException | TimeoutException | ElementClickInterceptedException e) {
			if(LOGGER.isWarnEnabled()) {
				LOGGER.warn(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}

		return webDriver.getPageSource();
	}
	
	public int get_DID() {
		return NumberUtils.toInt(env.getProperty("flow.value.did.empresa.consum"));
	}
}
