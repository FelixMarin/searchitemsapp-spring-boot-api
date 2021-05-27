package com.searchitemsapp.business.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
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
		
     	for (var i = 0; i < productDtoList.size(); i++) {
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
		
		var comparationResult= 0;
	
		if(primaryPrice.getOrdenacion() == 1) {
			
			var price = Optional.ofNullable(primaryPrice.getPrecio())
					.orElse(Constants.DEFAULT_PRICE.getValue());
			
			primaryPrice.setPrecio(price);
			
			comparationResult = doubleConverter(primaryPrice.getPrecio())
					.compareTo(doubleConverter(secondaryPrice.getPrecio()));

			putSamePrice(primaryPrice, secondaryPrice);
			cleanPrices(primaryPrice,secondaryPrice);
			
		} else if(primaryPrice.getOrdenacion() == 2) {
	
			var priceUnit = Optional.ofNullable(primaryPrice.getPrecioKilo())
					.filter(Strings::isNotBlank)
					.orElse(primaryPrice.getPrecio());
			
			primaryPrice.setPrecioKilo(priceUnit);
	
			putSamePrice(primaryPrice, secondaryPrice);

			comparationResult = doubleConverter(primaryPrice.getPrecioKilo())
					.compareTo(doubleConverter(secondaryPrice.getPrecioKilo()));

			cleanPrices(primaryPrice,secondaryPrice);
		}
		
		return comparationResult;
	}

	private  Double doubleConverter(final String price) {
		
		Optional<String> priceResult = Optional.ofNullable(convertToDecimal(price));
		
		var priceAux = priceResult
				.orElse(convertToInteger(price)).trim();
		
		return price.isBlank() || priceAux.isBlank()?
				Double.parseDouble(Constants.DEFAULT_PRICE.getValue()):
				Double.parseDouble(priceAux);
	}

	private  String convertToDecimal(final String price) {
		
		if(Constants.COMMA.getValue().equals(price)) {
			return Constants.DEFAULT_PRICE.getValue();
		}
	     
		var priceAux = checkIfHasPipeLine(price);
		
		var matcherDecimal = Pattern.compile(
			  Constants.DECIMAL_NUMBER_REGEX.getValue(), 
			  Pattern.MULTILINE).matcher(priceAux);

		var subPrice = StringUtils.EMPTY;
		
		if (matcherDecimal.find()) {
			subPrice = Optional.ofNullable(matcherDecimal.group(0))
				 			.orElse(convertToInteger(priceAux))
				 			.replace(Constants.COMMA.getValue(), Constants.DOT.getValue());
		}
	  
		subPrice = Optional.ofNullable(subPrice)
			.filter(StringUtils::isNoneBlank)
			.orElse(Constants.DEFAULT_PRICE.getValue());
		
		Double priceFinal = Double.parseDouble(subPrice);
		return priceFinal.toString();
	}
	
	private String checkIfHasPipeLine(String price) {
		var priceAux = price.replace(Constants.DOT.getValue(), Constants.COMMA.getValue());
		var hasPipe = priceAux.contains(Constants.PIPE.getValue());
		var iniIndex = priceAux.indexOf(Constants.PIPE.getValue())-1;
		var subPriceAux = iniIndex<1?priceAux:priceAux.substring(iniIndex, priceAux.length());
		return hasPipe?subPriceAux:priceAux;
	}

	private  String convertToInteger(final String price) {
		
		var matcherInteger = Pattern.compile(Constants.DECIMAL_NUMBER_REGEX.getValue(), 
				Pattern.MULTILINE).matcher(price);
		
	  if(matcherInteger.find()) {
		  return Constants.DECIMALS_EXTENSION.getValue();
	  }
		
	  var priceAux = price.replace(Constants.DOT.getValue(), StringUtils.EMPTY);
		
	  matcherInteger = Pattern.compile(Constants.DECIMAL_NUMBER_REGEX.getValue(), 
			  Pattern.MULTILINE).matcher(priceAux);
	  
	  Optional<String> priceResult = matcherInteger.find()?Optional.ofNullable(matcherInteger.group()):Optional.empty();
	  priceResult.ifPresent(elem -> elem = elem.concat(Constants.DECIMALS_EXTENSION.getValue()));
	  
	  priceResult.filter(StringUtils::isAllEmpty).ifPresent(elem -> {
	    	var matcher = Pattern.compile("\\d+", Pattern.MULTILINE).matcher(priceAux);
	    	elem = matcher.find()?matcher.group().concat(Constants.DECIMALS_EXTENSION.getValue()):elem;
	    });
		
		return priceResult.orElse(Constants.DEFAULT_PRICE.getValue());
	}
	
	private  void putSamePrice(final ProductDto primaryProduct, final ProductDto secondaryProduct) {
		
		Optional<String> primaryUnitPrice = Optional.ofNullable(primaryProduct.getPrecioKilo())
				.filter(StringUtils::isNoneBlank);
		
		Optional<String> secondaryUnitPrice = Optional.ofNullable(secondaryProduct.getPrecioKilo())
				.filter(StringUtils::isNoneBlank);
		
		var precioKilo = primaryUnitPrice.orElse(primaryProduct.getPrecio());
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
