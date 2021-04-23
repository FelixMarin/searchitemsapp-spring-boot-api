package com.searchitemsapp.company;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
	
	private Environment environment;

	@Override
	public List<String> getUrls(Document document, UrlDto urlDto)
			throws MalformedURLException {
			return Lists.newArrayList();
	}

	@Override
	public String getHtmlContent(final WebDriver webDriver, final String strUrl) 
			throws InterruptedException  {
		
		webDriver.get(strUrl);
		WebDriverWait wait = new WebDriverWait(webDriver, 50);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler"))).click();
		String res = webDriver.getPageSource();
		webDriver.close();
		return res;
	}
	public Long getId() {
		return NumberUtils.toLong(environment.getProperty("flow.value.did.empresa.consum"));
	}
	
	@Override
	public boolean isDynamic() {
		return true;
	}
}
