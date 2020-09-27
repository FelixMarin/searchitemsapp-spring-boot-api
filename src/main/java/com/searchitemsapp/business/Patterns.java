package com.searchitemsapp.business;

import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public interface Patterns {
	
	abstract public Elements selectScrapPattern(final Document document,
			final String strScrapPattern, final String strScrapNotPattern);
	
	abstract public Pattern createPatternProduct(final String[] arProducto);

}
