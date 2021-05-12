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
public class Hipercor implements Company {

	private Environment environment;

	@Override
	public List<String> getUrls(final Document document, 
			final UrlDto urlDto) throws MalformedURLException {
		
		var urlBase = urlDto.getNomUrl();		
		List<String> urls = Lists.newArrayList();
		urls.add(urlBase);		
		return urls;
	}
	
	@Override
	public String getHtmlContent(final WebDriver webDriver, final String strUrl) 
			throws InterruptedException  {
		
		webDriver.get(strUrl);
		var wait = new WebDriverWait(webDriver, 50);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("cookies-agree-all"))).click();
		var res = webDriver.getPageSource();
		webDriver.close();
		return res;
	}
	
	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public Long getId() {
		return NumberUtils.toLong(environment.getProperty("flow.value.did.empresa.hipercor"));
	}
}
