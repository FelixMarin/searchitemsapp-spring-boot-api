package com.searchitemsapp.validators;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Optional;
import com.google.common.collect.Maps;
import com.searchitemsapp.resource.Constants;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ListaProductosValidatorImpl implements ListaProductosValidator {
	
	public boolean isParams(String[] args,  MethodSignature mSignature) {
		
		var resultado = Maps.newHashMap();
		resultado.put(StringUtils.EMPTY, true);
		
		var inArgs = Optional.ofNullable(args).filter(StringUtils::isNoneBlank).orElse(new String[5]);
		
		if(validateParams(inArgs)) {
			
			Arrays.asList(inArgs).stream().forEach(value -> {
				
				var inValue = Optional.ofNullable(value)
						.filter(StringUtils::isNotBlank)
						.orElse(StringUtils.EMPTY);
				
				if(inValue.length() < 1 || inValue.length() > 47) {
					log.error(Arrays.toString(mSignature.getParameterNames()) + " valor de entrada: " + value );
					throw new IllegalArgumentException("valor no válido: " + inValue);
				}
				
				inValue = Pattern.compile(Constants.REGEX_SPECIAL_CHARACTERS.getValue())
						.matcher(inValue).find()?StringUtils.EMPTY:inValue;
				
				inValue = Pattern.compile(Constants.REGEX_WORDS.getValue())
						.matcher(inValue.toLowerCase()).find()?StringUtils.EMPTY:inValue;
				
				if(StringUtils.isBlank(inValue)) {
					log.error(Arrays.toString(mSignature.getParameterNames()) + " valor de entrada: " + value );
					resultado.put(StringUtils.EMPTY, false);
				}
			});
		} else {
			resultado.put(StringUtils.EMPTY, false);
		}
		
		return (boolean) resultado.get(StringUtils.EMPTY);
	}
	
	private boolean validateParams(String[] args) {
		
		var codEmpresa = Optional.ofNullable(args[4]).orElse(StringUtils.EMPTY);
		var codCategoria = Optional.ofNullable(args[2]).orElse(StringUtils.EMPTY);
			
		return (args.length == 5) &&
				(NumberUtils.isDigits(codCategoria) &&
						Pattern.compile(Constants.REGEX_ORDENACION.getValue()).matcher(codCategoria).find()) &&
				(Pattern.compile(Constants.REGEX_EMPRESAS.getValue()).matcher(codEmpresa).find() ||
						Pattern.compile(Constants.REGEX_ALL.getValue()).matcher(codEmpresa).find());
	}
	
}
