package com.searchitemsapp.business.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.Patterns;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PatternsImpl implements Patterns {

	@Override
	public Elements selectScrapPattern(final Document document,
			final String strScrapPattern, final String strScrapNotPattern) {

		return StringUtils.isAnyBlank(strScrapNotPattern)?
				document.select(strScrapPattern):
					document.select(strScrapPattern).not(strScrapNotPattern);
 
	}
	
	@Override
	public Pattern createPatternProduct(final String[] wordsProduct) {

		List<String> wordsList = Lists.newArrayList();
		
		List<String> wordsProductList = Arrays.asList(wordsProduct);  
		wordsProductList.forEach(singleWord -> wordsList.add(singleWord.toUpperCase()));
		
		var patternBuilder = new StringBuilder(10);
		
		patternBuilder.append("(");
		
		wordsList.forEach(singleWord -> patternBuilder.append(".*").append(singleWord));
		
		patternBuilder.append(")");
		
		Collections.reverse(wordsList);
		
		patternBuilder.append("|(");
		
		wordsList.forEach(singleWord -> patternBuilder.append(".*").append(singleWord));

		patternBuilder.append(")");
		
		return Pattern.compile(patternBuilder.toString());
	}
}
