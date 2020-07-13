package com.searchitemsapp.processdata.empresas;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;

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
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDTO;


/**
 * Módulo de scraping especifico diseñado para la 
 * extracción de datos del sitio web de Consum.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public class ProcessDataConsum implements IFProcessDataConsum {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataConsum.class);   

	private static final String SCROLL_INTO_VIEW = "arguments[0].scrollIntoView(true)";

	private ProcessDataConsum() {
		super();
	}
	
	/**
	 * Compone una lista de URLs de la empresa Consum.
	 * Con estas URLs se realizarán las peticiones al
	 * sitio web para extraer los datos. 
	 * 
	 * @param document
	 * @param urlDto
	 * @param selectorCssDto
	 * @return List<String>
	 * @exception MalformedURLException
	 */
	@Override
	public List<String> getListaUrls(Document document, UrlDTO urlDto)
			throws MalformedURLException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		String urlBase = urlDto.getNomUrl();
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);
		
		return listaUrls;
	}

	/**
	 * Se obtiene el sitio web utilizando el web driver. 
	 * De este modo se puede obtener un sitio web después 
	 * de ejecutar javascript.
	 * 
	 * @param webDriver
	 * @param strUrl
	 * @return String
	 * @throws InterruptedException
	 */
	public String getHtmlContent(final WebDriver webDriver, final String strUrl) throws InterruptedException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
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
}
