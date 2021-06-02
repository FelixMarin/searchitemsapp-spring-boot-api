package com.searchitemsapp.validators;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.resource.Constants;

@Component
public class ListaProductosValidatorImpl implements ListaProductosValidator {
	
	public boolean isParams(String[] args) {
		
		var inArgs = Optional.ofNullable(args)
				.filter(StringUtils::isNoneBlank)
				.orElse(new String[5]);
		
		var validatedParams = validateParams(inArgs);
		
		if(validatedParams.isEmpty()) {
			return false;
		}
		
		List<String> filteredParams = Lists.newArrayList();
			
		Arrays.asList(inArgs).stream().forEach(value -> {
			
			var inValue = Optional.ofNullable(value)
					.filter(StringUtils::isNotBlank)
					.filter(elem -> elem.length() > 0 && elem.length() < 47)
					.filter(elem -> !Pattern.compile(Constants.REGEX_SPECIAL_CHARACTERS.getValue()).matcher(elem).find())
					.filter(elem -> !Pattern.compile(Constants.REGEX_WORDS.getValue()).matcher(elem.toLowerCase()).find())
					.orElse(StringUtils.EMPTY);
			
			if(inValue.isEmpty()) {
				filteredParams.add(StringUtils.EMPTY);
			}
		});
		
		return filteredParams.stream().allMatch(StringUtils::isNotBlank);
	}
	
	private String validateParams(String[] args) {
		
		var codEmpresa = Optional.ofNullable(args[4])
				.filter(elem -> Pattern.compile(Constants.REGEX_EMPRESAS.getValue()).matcher(elem).find())
				.orElse(StringUtils.EMPTY);
		
		var allCompanies = Optional.ofNullable(args[4])
				.filter(elem -> Pattern.compile(Constants.REGEX_ALL.getValue()).matcher(elem).find())
				.orElse(StringUtils.EMPTY);
		
		var codOrdenacion = Optional.ofNullable(args[2])
				.filter(NumberUtils::isDigits)
				.filter(elem -> Pattern.compile(Constants.REGEX_ORDENACION.getValue()).matcher(elem).find())
				.orElse(StringUtils.EMPTY);
		
		var bolFilter = (StringUtils.isNotBlank(codEmpresa) || StringUtils.isNotBlank(allCompanies)) &&
				StringUtils.isNotBlank(codOrdenacion);
			
		return bolFilter?Arrays.toString(args):StringUtils.EMPTY;
	}

	@Override
	public boolean isParamsLiveSearch(String arg) {
		
		var validatedArgument = Optional.ofNullable(arg);
		
		var filteredArgument = validatedArgument
				.filter(StringUtils::isNotBlank)
				.filter(elem -> !Pattern.compile(Constants.REGEX_WORDS.getValue()).matcher(elem.toLowerCase()).find())
				.filter(elem -> !Pattern.compile(Constants.REGEX_SPECIAL_CHARACTERS.getValue()).matcher(elem).find());
		
		return filteredArgument.isPresent();
	}
	
}
