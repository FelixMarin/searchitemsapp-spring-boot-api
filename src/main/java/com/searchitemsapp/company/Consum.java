package com.searchitemsapp.company;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Consum implements Company {
	
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
			throws InterruptedException  {
		
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		
		webDriver.get(strUrl);		
		WebDriverWait wait = new WebDriverWait(webDriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler"))).click();
		
		for (int i = 0; i < 2; i++) {
			
			Optional<WebElement> wButton = 
					Optional.of(wait.until(ExpectedConditions
							.elementToBeClickable(By.className("grid__footer-viewMore"))));
			
			wButton.ifPresent(elem -> { 
				
				js.executeScript(SCROLL_INTO_VIEW, elem);
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				
				wait.until(ExpectedConditions.visibilityOf(elem));
				elem.click();
			});
		}
		
		return webDriver.getPageSource();
	}
	public Long getId() {
		return NumberUtils.toLong(environment.getProperty("flow.value.did.empresa.consum"));
	}
	
	@Override
	public boolean isDynamic() {
		return true;
	}
}
