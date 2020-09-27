package com.searchitemsapp.business.impl;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.searchitemsapp.business.Prices;
import com.searchitemsapp.dto.ProductDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PricesImpl implements Prices {
	
	private static final transient String DECIMALS_EXTENSION = ".00";
	private static final transient String DECIMAL_NUMBER_REGEX = "(\\d*[,][0-9]*)|([0-9]{1,9})";
	private static final transient String KILOGRAM_EXTENSION = "/kg";
	private static final transient double DEFAULT_PRICE = 1000.00;	
	private static final transient String PIPE = "|";
	private static final transient String COMMA = ",";
	private static final transient String DOT = ".";	
	private static final transient String EURO_EXTENSION = "/Eur";

	@Override
	public List<ProductDto> sortList(List<ProductDto> productDtoList) {
		productDtoList.sort(this);
		return productDtoList;
	}
	
	@Override
	public int compare(ProductDto primaryPrice, ProductDto secondaryPrice) {
		return priceComparator(primaryPrice, secondaryPrice);
	}
	
	@Override
	public int priceComparator(final ProductDto primaryPrice, final ProductDto secondaryPrice) {
		
		int comparationResult= 0;
	
		if(primaryPrice.getOrdenacion() == 1) {

			if(StringUtils.isAllEmpty(primaryPrice.getPrecio())) {
				primaryPrice.setPrecio(String.valueOf(DEFAULT_PRICE));
			}

			comparationResult = doubleConverter(primaryPrice.getPrecio())
					.compareTo(doubleConverter(secondaryPrice.getPrecio()));

			cleanPrices(primaryPrice,secondaryPrice);
			
		} else if(primaryPrice.getOrdenacion() == 2) {
	
			if(Objects.isNull(primaryPrice.getPrecioKilo()) || 
					StringUtils.isAllEmpty(primaryPrice.getPrecioKilo())) {
				primaryPrice.setPrecioKilo(String.valueOf(DEFAULT_PRICE));
			}

			putSamePrice(primaryPrice, secondaryPrice);

			comparationResult = doubleConverter(primaryPrice.getPrecioKilo())
					.compareTo(doubleConverter(secondaryPrice.getPrecioKilo()));

			cleanPrices(primaryPrice,secondaryPrice);
		}
		
		return comparationResult;
	}

	private  Double doubleConverter(final String strPrecioKilo) {
		
		String strPrecioKiloRes = convertToDecimal(strPrecioKilo);
		
		if(StringUtils.isAllEmpty(strPrecioKiloRes)) {
			strPrecioKiloRes = convertToInteger(strPrecioKilo);
		}
 
		strPrecioKiloRes = strPrecioKiloRes.trim();
		return Double.parseDouble(strPrecioKiloRes);
	}

	private  String convertToDecimal(final String price) {
		
		if(COMMA.equals(price)) {
			return "1000.00";
		}
	     
	  String priceResult = StringUtils.EMPTY;
	  
	  String priceAux = price.replace(DOT, StringUtils.EMPTY);
	 
	  if(priceAux.contains(PIPE)) {
		  priceAux = priceAux.substring(
				  priceAux.lastIndexOf(PIPE)+1,
				  priceAux.length()).trim().
				  replaceAll(StringUtils.SPACE
						  .concat(StringUtils.SPACE),
						  StringUtils.SPACE);
	  }

	  Matcher matcherDecimal = Pattern.compile(
			  DECIMAL_NUMBER_REGEX, Pattern.MULTILINE).matcher(priceAux);

	  if (matcherDecimal.find()) {
	     priceResult = matcherDecimal.group(0);
	  }

	  if(!StringUtils.isAllEmpty(priceResult)) {	  
		  priceResult = priceResult.replace(COMMA, DOT);
	  } else {
		  return convertToInteger(priceAux);
	  }
	  
	  return priceResult;
	}

	private  String convertToInteger(final String price) {
		
		String priceResult = StringUtils.EMPTY;		
		Matcher matcherInteger = Pattern.compile(DECIMAL_NUMBER_REGEX, Pattern.MULTILINE).matcher(price);
		
	  if(matcherInteger.find()) {
		  return DECIMALS_EXTENSION;
	  }
		
	  String priceAux = price.replace(DOT, StringUtils.EMPTY);
		
	  matcherInteger = Pattern.compile(DECIMAL_NUMBER_REGEX, Pattern.MULTILINE).matcher(priceAux);
		
	    if(matcherInteger.find()) {
		   priceResult = matcherInteger.group();
		   priceResult = priceResult.concat(DECIMALS_EXTENSION);
	    }

		if(StringUtils.isAllEmpty(priceResult)) {
			  matcherInteger = Pattern.compile("\\d+", Pattern.MULTILINE).matcher(priceAux);

			if(matcherInteger.find()) {
				  priceResult = matcherInteger.group();
				  priceResult = priceResult.concat(DECIMALS_EXTENSION);
			}
		}
		
		return priceResult;
	}
	
	private  void putSamePrice(final ProductDto precioPrimario, final ProductDto precioSecundario) {
		
		if(StringUtils.isAllEmpty(precioPrimario.getPrecioKilo()) &&
				!StringUtils.isAllEmpty(precioPrimario.getPrecio())) {
			precioPrimario.setPrecioKilo(convertToDecimal(precioPrimario.getPrecio())
					.concat(KILOGRAM_EXTENSION));
		}
		
		if(StringUtils.isAllEmpty(precioSecundario.getPrecioKilo()) &&
				!StringUtils.isAllEmpty(precioSecundario.getPrecio())) {
			precioSecundario.setPrecioKilo(convertToDecimal(precioSecundario.getPrecio())
					.concat(KILOGRAM_EXTENSION));
		}
	}

	private  void cleanPrices(final ProductDto precioPrimario, final ProductDto precioSecundario) {
		
		if(!StringUtils.isAllEmpty(precioPrimario.getPrecio())) {
			precioPrimario.setPrecio(convertToDecimal(precioPrimario.getPrecio())
					.concat(EURO_EXTENSION)
					.replace(DOT,COMMA));	
			
			precioSecundario.setPrecio(convertToDecimal(precioSecundario.getPrecio())
					.concat(EURO_EXTENSION)
					.replace(DOT,COMMA));
		}
		
		if(!StringUtils.isAllEmpty(precioPrimario.getPrecioKilo())) {
			precioPrimario.setPrecioKilo(convertToDecimal(precioPrimario.getPrecioKilo())
			.concat(KILOGRAM_EXTENSION)
			.replace(DOT,COMMA));	
	
			precioSecundario.setPrecioKilo(convertToDecimal(precioSecundario.getPrecioKilo())
			.concat(KILOGRAM_EXTENSION)
			.replace(DOT,COMMA));	
		}
	}
}
