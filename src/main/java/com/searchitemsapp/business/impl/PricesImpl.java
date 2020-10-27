package com.searchitemsapp.business.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.searchitemsapp.business.Prices;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.resource.Constants;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PricesImpl implements Prices {
	
	@Override
	public List<ProductDto> sortProductsByPrice(List<ProductDto> productDtoList) {
		productDtoList.sort(this);
		
     	for (int i = 0; i < productDtoList.size(); i++) {
     		productDtoList.get(i).setIdentificador(i+1);
		}
		
		return productDtoList;
	}
	
	@Override
	public int compare(ProductDto primaryPrice,ProductDto secondaryPrice) {
		return priceComparator(primaryPrice, secondaryPrice);
	}
	
	@Override
	public int priceComparator(final ProductDto primaryPrice, final ProductDto secondaryPrice) {
		
		int comparationResult= 0;
	
		if(primaryPrice.getOrdenacion() == 1) {

			if(StringUtils.isAllEmpty(primaryPrice.getPrecio())) {
				primaryPrice.setPrecio(Constants.DEFAULT_PRICE.getValue());
			}

			comparationResult = doubleConverter(primaryPrice.getPrecio())
					.compareTo(doubleConverter(secondaryPrice.getPrecio()));

			cleanPrices(primaryPrice,secondaryPrice);
			
		} else if(primaryPrice.getOrdenacion() == 2) {
	
			if(Objects.isNull(primaryPrice.getPrecioKilo()) || 
					StringUtils.isAllEmpty(primaryPrice.getPrecioKilo())) {
				primaryPrice.setPrecioKilo(Constants.DEFAULT_PRICE.getValue());
			}

			putSamePrice(primaryPrice, secondaryPrice);

			comparationResult = doubleConverter(primaryPrice.getPrecioKilo())
					.compareTo(doubleConverter(secondaryPrice.getPrecioKilo()));

			cleanPrices(primaryPrice,secondaryPrice);
		}
		
		return comparationResult;
	}

	private  Double doubleConverter(final String price) {
		
		Optional<String> priceResult = Optional.ofNullable(convertToDecimal(price));
		String priceAux = priceResult.orElse(convertToInteger(price)).trim();
		return price.isBlank() || priceAux.isBlank()?
				Double.parseDouble(Constants.DEFAULT_PRICE.getValue()):
				Double.parseDouble(priceAux);
	}

	private  String convertToDecimal(final String price) {
		
		if(Constants.COMMA.getValue().equals(price)) {
			return Constants.DEFAULT_PRICE.getValue();
		}
	     
		String priceResult = StringUtils.EMPTY;
	  
		String priceAux = price.replace(Constants.DOT.getValue(), StringUtils.EMPTY);
	
		Matcher matcherDecimal = Pattern.compile(
			  Constants.DECIMAL_NUMBER_REGEX.getValue(), Pattern.MULTILINE).matcher(priceAux);

		if (matcherDecimal.find()) {
			priceResult = Optional.ofNullable(matcherDecimal.group(0))
				 			.orElse(convertToInteger(priceAux))
				 			.replace(Constants.COMMA.getValue(), Constants.DOT.getValue());
		}
	  
		return priceResult;
	}

	private  String convertToInteger(final String price) {
		
		Matcher matcherInteger = Pattern.compile(Constants.DECIMAL_NUMBER_REGEX.getValue(), Pattern.MULTILINE).matcher(price);
		
	  if(matcherInteger.find()) {
		  return Constants.DECIMALS_EXTENSION.getValue();
	  }
		
	  String priceAux = price.replace(Constants.DOT.getValue(), StringUtils.EMPTY);
		
	  matcherInteger = Pattern.compile(Constants.DECIMAL_NUMBER_REGEX.getValue(), Pattern.MULTILINE).matcher(priceAux);
	  String priceResult = StringUtils.EMPTY;
	  
	    if(matcherInteger.find()) {
		   priceResult = matcherInteger.group();
		   priceResult = priceResult.concat(Constants.DECIMALS_EXTENSION.getValue());
	    }

		if(StringUtils.isAllEmpty(priceResult)) {
			  matcherInteger = Pattern.compile("\\d+", Pattern.MULTILINE).matcher(priceAux);

			if(matcherInteger.find()) {
				  priceResult = matcherInteger.group();
				  priceResult = priceResult.concat(Constants.DECIMALS_EXTENSION.getValue());
			}
		}
		
		return priceResult;
	}
	
	private  void putSamePrice(final ProductDto primaryProduct, final ProductDto secondaryProduct) {
		
		Optional<String> primaryUnitPrice = Optional.ofNullable(primaryProduct.getPrecioKilo());
		Optional<String> secondaryUnitPrice = Optional.ofNullable(secondaryProduct.getPrecioKilo());
		
		String precioKilo = primaryUnitPrice.orElse(primaryProduct.getPrecio());
		precioKilo = convertToDecimal(precioKilo);
		precioKilo = precioKilo.concat(Constants.KILOGRAM_EXTENSION.getValue());
		primaryProduct.setPrecioKilo(precioKilo);
		
		precioKilo = secondaryUnitPrice.orElse(secondaryProduct.getPrecio());
		precioKilo = convertToDecimal(precioKilo);
		precioKilo = precioKilo.concat(Constants.KILOGRAM_EXTENSION.getValue());
		secondaryProduct.setPrecioKilo(precioKilo);
		
	}

	private  void cleanPrices(final ProductDto primaryPrice, final ProductDto secondaryPrice) {
		
		Optional<String> oprimaryPrice = Optional.ofNullable(primaryPrice.getPrecio());
		
		oprimaryPrice.ifPresent(elem -> {
			primaryPrice.setPrecio(convertToDecimal(primaryPrice.getPrecio())
					.concat(Constants.EURO_EXTENSION.getValue())
					.replace(Constants.DOT.getValue(),Constants.COMMA.getValue()));	
			
			secondaryPrice.setPrecio(convertToDecimal(secondaryPrice.getPrecio())
					.concat(Constants.EURO_EXTENSION.getValue())
					.replace(Constants.DOT.getValue(),Constants.COMMA.getValue()));
		});
	}
}
