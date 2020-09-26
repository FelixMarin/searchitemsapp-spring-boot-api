package com.searchitemsapp.business.impl;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.searchitemsapp.business.PriceManager;
import com.searchitemsapp.dto.ProductDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PriceManagerImpl implements PriceManager {
	
	private static final transient String DECIMALES = ".00";
	private static final transient String REGEXNUMERODECIMAL = "(\\d*[,][0-9]*)|([0-9]{1,9})";
	private static final transient String BARRAKILOGRAM = "/kg";
	private static final transient double DEFAULTPRICE = 1000.00;	
	private static final transient String PIPESTRING = "|";
	private static final transient String COMMASTRING = ",";
	private static final transient String DOTSTRING = ".";	
	private static final transient String EUROSYMBOL = "/Eur";

	public List<ProductDto> ordenarLista(List<ProductDto> lista) {
		lista.sort(this);
		return lista;
	}
	
	@Override
	public int compare(ProductDto precioPrimario, ProductDto precioSecundario) {
		return priceComparator(precioPrimario, precioSecundario);
	}
	
	public int priceComparator(final ProductDto primaryPrice, final ProductDto secondaryPrice) {
		
		int comparationResult= 0;
	
		if(primaryPrice.getOrdenacion() == 1) {

			if(StringUtils.isAllEmpty(primaryPrice.getPrecio())) {
				primaryPrice.setPrecio(String.valueOf(DEFAULTPRICE));
			}

			comparationResult = doubleConverter(primaryPrice.getPrecio())
					.compareTo(doubleConverter(secondaryPrice.getPrecio()));

			cleanPrices(primaryPrice,secondaryPrice);
			
		} else if(primaryPrice.getOrdenacion() == 2) {
	
			if(Objects.isNull(primaryPrice.getPrecioKilo()) || 
					StringUtils.isAllEmpty(primaryPrice.getPrecioKilo())) {
				primaryPrice.setPrecioKilo(String.valueOf(DEFAULTPRICE));
			}

			putSamePrice(primaryPrice, secondaryPrice);

			comparationResult = doubleConverter(primaryPrice.getPrecioKilo())
					.compareTo(doubleConverter(secondaryPrice.getPrecioKilo()));

			cleanPrices(primaryPrice,secondaryPrice);
		}
		
		return comparationResult;
	}

	private  Double doubleConverter(final String strPrecioKilo) {
		
		String strPrecioKiloRes = addDecimalValue(strPrecioKilo);
		
		if(StringUtils.isAllEmpty(strPrecioKiloRes)) {
			strPrecioKiloRes = convertToInteger(strPrecioKilo);
		}
 
		strPrecioKiloRes = strPrecioKiloRes.trim();
		return Double.parseDouble(strPrecioKiloRes);
	}

	private  String addDecimalValue(final String cadena) {
		
		if(COMMASTRING.equals(cadena)) {
			return "1000.00";
		}
	     
	  String resultado = StringUtils.EMPTY;
	  
	  String cadenaAux = cadena.replace(DOTSTRING, StringUtils.EMPTY);
	 
	  if(cadenaAux.contains(PIPESTRING)) {
		  cadenaAux = cadenaAux.substring(
				  cadenaAux.lastIndexOf(PIPESTRING)+1,
				  cadenaAux.length()).trim().
				  replaceAll(StringUtils.SPACE
						  .concat(StringUtils.SPACE),
						  StringUtils.SPACE);
	  }

	  Matcher mDecimal = Pattern.compile(
			  REGEXNUMERODECIMAL, Pattern.MULTILINE).matcher(cadenaAux);

	  if (mDecimal.find()) {
	     resultado = mDecimal.group(0);
	  }

	  if(!StringUtils.isAllEmpty(resultado)) {	  
		  resultado = resultado.replace(COMMASTRING, DOTSTRING);
	  } else {
		  return convertToInteger(cadenaAux);
	  }
	  
	  return resultado;
	}

	private  String convertToInteger(final String strPrecio) {
		
		String strResultado = StringUtils.EMPTY;		
		Matcher mEntero = Pattern.compile(REGEXNUMERODECIMAL, Pattern.MULTILINE).matcher(strPrecio);
		
	  if(mEntero.find()) {
		  return DECIMALES;
	  }
		
	  String cadenaAux = strPrecio.replace(DOTSTRING, StringUtils.EMPTY);
		
	  mEntero = Pattern.compile(REGEXNUMERODECIMAL, Pattern.MULTILINE).matcher(cadenaAux);
		
	    if(mEntero.find()) {
		   strResultado = mEntero.group();
		   strResultado = strResultado.concat(DECIMALES);
	    }

		if(StringUtils.isAllEmpty(strResultado)) {
			  mEntero = Pattern.compile("\\d+", Pattern.MULTILINE).matcher(cadenaAux);

			if(mEntero.find()) {
				  strResultado = mEntero.group();
				  strResultado = strResultado.concat(DECIMALES);
			}
		}
		
		return strResultado;
	}
	
	private  void putSamePrice(final ProductDto precioPrimario, final ProductDto precioSecundario) {
		
		if(StringUtils.isAllEmpty(precioPrimario.getPrecioKilo()) &&
				!StringUtils.isAllEmpty(precioPrimario.getPrecio())) {
			precioPrimario.setPrecioKilo(addDecimalValue(precioPrimario.getPrecio())
					.concat(BARRAKILOGRAM));
		}
		
		if(StringUtils.isAllEmpty(precioSecundario.getPrecioKilo()) &&
				!StringUtils.isAllEmpty(precioSecundario.getPrecio())) {
			precioSecundario.setPrecioKilo(addDecimalValue(precioSecundario.getPrecio())
					.concat(BARRAKILOGRAM));
		}
	}

	private  void cleanPrices(final ProductDto precioPrimario, final ProductDto precioSecundario) {
		
		if(!StringUtils.isAllEmpty(precioPrimario.getPrecio())) {
			precioPrimario.setPrecio(addDecimalValue(precioPrimario.getPrecio())
					.concat(EUROSYMBOL)
					.replace(DOTSTRING,COMMASTRING));	
			
			precioSecundario.setPrecio(addDecimalValue(precioSecundario.getPrecio())
					.concat(EUROSYMBOL)
					.replace(DOTSTRING,COMMASTRING));
		}
		
		if(!StringUtils.isAllEmpty(precioPrimario.getPrecioKilo())) {
			precioPrimario.setPrecioKilo(addDecimalValue(precioPrimario.getPrecioKilo())
			.concat(BARRAKILOGRAM)
			.replace(DOTSTRING,COMMASTRING));	
	
			precioSecundario.setPrecioKilo(addDecimalValue(precioSecundario.getPrecioKilo())
			.concat(BARRAKILOGRAM)
			.replace(DOTSTRING,COMMASTRING));	
		}
	}
}
