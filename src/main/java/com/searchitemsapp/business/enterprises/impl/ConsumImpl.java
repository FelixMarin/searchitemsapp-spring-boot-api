package com.searchitemsapp.business.enterprises.impl;

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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.enterprises.Company;
import com.searchitemsapp.dto.UrlDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ConsumImpl implements Company {
	
	private static final String SCROLL_INTO_VIEW = "arguments[0].scrollIntoView(true)";
	
	private Environment environment;

	@Override
	public List<String> getUrls(Document document, UrlDto urlDto)
			throws MalformedURLException {

		String urlBase = urlDto.getNomUrl();
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);
		
		return listaUrls;
	}

	@Override
	public String getHtmlContent(final WebDriver webDriver, final String strUrl) 
			throws InterruptedException, ElementNotVisibleException, 
			TimeoutException, ElementClickInterceptedException {
		
		WebElement wButton;
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		boolean isButton = Boolean.TRUE;
		
		webDriver.get(strUrl);		
		WebDriverWait wait = new WebDriverWait(webDriver, 30);
			
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


		return webDriver.getPageSource();
	}
	
	public int get_DID() {
		return NumberUtils.toInt(environment.getProperty("flow.value.did.empresa.consum"));
	}
	
	@Override
	public boolean isDynamic() {
		return true;
	}
}
