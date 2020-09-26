package com.searchitemsapp.business.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.PatternsManager;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PatternsManagerImpl implements PatternsManager {

	public Elements selectScrapPattern(final Document document,
			final String strScrapPattern, final String strScrapNotPattern) {

		Elements documentElements;

        if(Objects.isNull(strScrapNotPattern)) {
        	documentElements = document.select(strScrapPattern);
        } else {
        	documentElements = document.select(strScrapPattern).not(strScrapNotPattern);
        }

        return documentElements;
	}
	
	public Pattern createPatternProduct(final String[] arProducto) {

		List<String> tokens = Lists.newArrayList();
		
		List<String> listProducto = Arrays.asList(arProducto);  
		listProducto.forEach(elem -> tokens.add(elem.toUpperCase()));
		
		StringBuilder stringBuilder = new StringBuilder(10);
		
		stringBuilder.append("(");
		
		tokens.forEach(e -> stringBuilder.append(".*").append(e));
		
		stringBuilder.append(")");
		
		Collections.reverse(tokens);
		
		stringBuilder.append("|(");
		
		tokens.forEach(e -> stringBuilder.append(".*").append(e));

		stringBuilder.append(")");
		
		return Pattern.compile(stringBuilder.toString());
	}
}
