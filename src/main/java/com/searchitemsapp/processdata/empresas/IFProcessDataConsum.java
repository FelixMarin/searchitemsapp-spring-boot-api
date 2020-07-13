package com.searchitemsapp.processdata.empresas;

import org.openqa.selenium.WebDriver;

public interface IFProcessDataConsum extends IFProcessDataEmpresas {
	public String getHtmlContent(final WebDriver webDriver, final String strUrl) throws InterruptedException;
}
