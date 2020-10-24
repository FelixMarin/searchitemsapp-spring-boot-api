package com.searchitemsapp.business;

import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public interface Patterns {
	
	abstract Elements selectScrapPattern(final Document document,
			final String scrapPattern, final String scrapNotPattern);
	
	abstract Pattern createPatternProduct(final String[] productsArray);

}
