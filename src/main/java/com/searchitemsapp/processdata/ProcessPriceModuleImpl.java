package com.searchitemsapp.processdata;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.UnsupportedAttributeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessPriceModuleImpl implements ProcessPrice {
	
	private static final transient Logger LOGGER = LoggerFactory.getLogger(ProcessPriceModuleImpl.class); 
	
	private static final transient String DECIMALES = ".00";
	private static final transient String REGEXNUMERODECIMAL = "(\\d*[,][0-9]*)|([0-9]{1,9})";
	private static final transient String BARRAKILOGRAM = "/kg";
	private static final transient double DEFAULTPRICE = 1000.00;	
	private static final transient String PIPESTRING = "|";
	private static final transient String COMMASTRING = ",";
	private static final transient String DOTSTRING = ".";	
	private static final transient String EUROSYMBOL = "/Eur";
	
	private int identificador;
	private String nomProducto;
	private String desProducto;
	private Integer didEmpresa;
	private String nomEmpresa;
	private String precioKilo;
	private String imagen;
	private String precio;
	private String nomUrl;
	private Integer didUrl;
	private boolean bolActivo;
	private boolean bolStatus;
	private Boolean bolLogin;	
	private String desUrl;
	private String nomUrlAllProducts;
	private int ordenacion;

	public List<ProcessPrice> ordenarLista(List<ProcessPrice> lista) {
		lista.sort(this);
		return lista;
	}
	
	@Override
	public int compare(ProcessPrice precioPrimario, ProcessPrice precioSecundario) {
		return processPrice(precioPrimario, precioSecundario);
	}
	
	public int processPrice(final ProcessPrice precioPrimario, final ProcessPrice precioSecundario) {
		
		int iResultado= 0;

		boolean bCheck = precioPrimario.getOrdenacion() == 1 || precioPrimario.getOrdenacion() == 2;
	
		if(bCheck) {
	
			if(precioPrimario.getOrdenacion() == 1) {
	
				if(StringUtils.isAllEmpty(precioPrimario.getPrecio())) {
					precioPrimario.setPrecio(String.valueOf(DEFAULTPRICE));
				}

				iResultado = convertirDouble(precioPrimario.getPrecio())
						.compareTo(convertirDouble(precioSecundario.getPrecio()));
	
				estilizarPrecios(precioPrimario,precioSecundario);
				
			} else if(precioPrimario.getOrdenacion() == 2) {
		
				if(Objects.isNull(precioPrimario.getPrecioKilo()) || 
						StringUtils.isAllEmpty(precioPrimario.getPrecioKilo())) {
					precioPrimario.setPrecioKilo(String.valueOf(DEFAULTPRICE));
				}

				mismoPrecioYPrecioKilo(precioPrimario, precioSecundario);
	
				iResultado = convertirDouble(precioPrimario.getPrecioKilo())
						.compareTo(convertirDouble(precioSecundario.getPrecioKilo()));

				estilizarPrecios(precioPrimario,precioSecundario);
			}
		} else {
			UnsupportedAttributeException e = new UnsupportedAttributeException("Error en el parametro de ordenacion", String.valueOf(precioPrimario.getOrdenacion()));
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
			throw e;
		}
		
		return iResultado;
	}

	private  Double convertirDouble(final String strPrecioKilo) {
		
		String strPrecioKiloRes = extraerDecimal(strPrecioKilo);
		
		if(StringUtils.isAllEmpty(strPrecioKiloRes)) {
			strPrecioKiloRes = extraerEntero(strPrecioKilo);
		}
 
		strPrecioKiloRes = strPrecioKiloRes.trim();
		return Double.parseDouble(strPrecioKiloRes);
	}

	private  String extraerDecimal(final String cadena) {
		
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
		  return extraerEntero(cadenaAux);
	  }
	  
	  return resultado;
	}

	private  String extraerEntero(final String strPrecio) {
		
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
	
	private  void mismoPrecioYPrecioKilo(ProcessPrice precioPrimario, ProcessPrice precioSecundario) {
		
		if(StringUtils.isAllEmpty(precioPrimario.getPrecioKilo()) &&
				!StringUtils.isAllEmpty(precioPrimario.getPrecio())) {
			precioPrimario.setPrecioKilo(extraerDecimal(precioPrimario.getPrecio())
					.concat(BARRAKILOGRAM));
		}
		
		if(StringUtils.isAllEmpty(precioSecundario.getPrecioKilo()) &&
				!StringUtils.isAllEmpty(precioSecundario.getPrecio())) {
			precioSecundario.setPrecioKilo(extraerDecimal(precioSecundario.getPrecio())
					.concat(BARRAKILOGRAM));
		}
	}

	private  void estilizarPrecios(final ProcessPrice precioPrimario, final ProcessPrice precioSecundario) {
		
		if(!StringUtils.isAllEmpty(precioPrimario.getPrecio())) {
			precioPrimario.setPrecio(extraerDecimal(precioPrimario.getPrecio())
					.concat(EUROSYMBOL)
					.replace(DOTSTRING,COMMASTRING));	
			
			precioSecundario.setPrecio(extraerDecimal(precioSecundario.getPrecio())
					.concat(EUROSYMBOL)
					.replace(DOTSTRING,COMMASTRING));
		}
		
		if(!StringUtils.isAllEmpty(precioPrimario.getPrecioKilo())) {
			precioPrimario.setPrecioKilo(extraerDecimal(precioPrimario.getPrecioKilo())
			.concat(BARRAKILOGRAM)
			.replace(DOTSTRING,COMMASTRING));	
	
			precioSecundario.setPrecioKilo(extraerDecimal(precioSecundario.getPrecioKilo())
			.concat(BARRAKILOGRAM)
			.replace(DOTSTRING,COMMASTRING));	
		}
	}
}
