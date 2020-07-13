package com.searchitemsapp.processdata;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.UnsupportedAttributeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sun.istack.NotNull;

/**
 * Objeto de Transferencia de Datos (DTO) 
 * es un objeto que transporta datos entre procesos.
 * No tiene más comportamiento que almacenar y entregar 
 * sus propios datos.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public class ProcessPriceModule implements IFProcessPrice {
	
	private transient final Logger LOGGER = LoggerFactory.getLogger(IFProcessPrice.class); 
	
	private transient final String DECIMALES = ".00";
	private transient final String DEFAULT_STR_PRICE = "1000.00";
	private transient final String REGEX_NUMERO_DECIMAL = "(\\d*[,][0-9]*)|([0-9]{1,9})";
	private transient final String REGEX_INTEGER = "\\d+";
	private transient final String BARRA_KILO_GRAM = "/kg";
	private transient final double DEFAULT_PRICE = 1000.00;
	
	private transient final String PIPE_STRING = "|";
	private transient final String COMMA_STRING = ",";
	private transient final String DOT_STRING = ".";
	
	private transient final String EURO_SYMBOL = "/Eur";
	
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
	
	public ProcessPriceModule() {
		super();
	}
	
	public int getIdentificador() {
		return identificador;
	}
	
	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}
	
	public String getNomProducto() {
		return nomProducto;
	}
	
	public void setNomProducto(String nomProducto) {
		this.nomProducto = nomProducto;
	}
	
	public String getDesProducto() {
		return desProducto;
	}
	
	public void setDesProducto(String desProducto) {
		this.desProducto = desProducto;
	}
	
	public String getImagen() {
		return imagen;	
	}
	
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	public String getPrecio() {
		return precio;
	}
	
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	
	public Integer getDidEmpresa() {
		return didEmpresa;
	}
	
	public void setDidEmpresa(Integer didEmpresa) {
		this.didEmpresa = didEmpresa;
	}
	
	public String getPrecioKilo() {
		return precioKilo;
	}
	
	public void setPrecioKilo(String precioKilo) {
		this.precioKilo = precioKilo;
	}
	
	public String getNomUrl() {
		return nomUrl;
	}
	
	public void setNomUrl(String nomUrl) {
		this.nomUrl = nomUrl;
	}
	
	public Integer getDidUrl() {
		return didUrl;
	}
	
	public void setDidUrl(Integer didUrl) {
		this.didUrl = didUrl;
	}
	
	public boolean isBolActivo() {
		return bolActivo;
	}
	
	public void setBolActivo(boolean bolActivo) {
		this.bolActivo = bolActivo;
	}
	
	public boolean isBolStatus() {
		return bolStatus;
	}
	
	public void setBolStatus(boolean bolStatus) {
		this.bolStatus = bolStatus;
	}
	
	public Boolean isBolLogin() {
		return bolLogin;
	}
	
	public void setBolLogin(Boolean bolLogin) {
		this.bolLogin = bolLogin;
	}
	
	public String getDesUrl() {
		return desUrl;
	}
	
	public void setDesUrl(String desUrl) {
		this.desUrl = desUrl;
	}
	
	public String getNomUrlAllProducts() {
		return nomUrlAllProducts;
	}
	
	public void setNomUrlAllProducts(String nomUrlAllProducts) {
		this.nomUrlAllProducts = nomUrlAllProducts;
	}
	
	public int getOrdenacion() {
		return ordenacion;
	}
	
	public void setOrdenacion(int ordenacion) {
		this.ordenacion = ordenacion;
	}
	
	public String getNomEmpresa() {
		return nomEmpresa;
	}
	
	public void setNomEmpresa(String nomEmpresa) {
		this.nomEmpresa = nomEmpresa;
	}
	
	/**
	 * Recibe dos objetos de con los precios de los productos. Los precios
	 * en formato string son tratados para poder ser convertidos en numeros
	 * decimales. Se comparan y se devuelve el resultado de la comparación.
	 * 
	 * @param a
	 * @param b
	 * @return int
	 * @exception UnsupportedAttributeException
	 */
	public int processPrice(final IFProcessPrice a, final IFProcessPrice b) {
		
		int i= 0;

		boolean bCheck = a.getOrdenacion() == 1 || a.getOrdenacion() == 2;
	
		if(bCheck) {
	
			if(a.getOrdenacion() == 1) {
	
				if(StringUtils.isAllEmpty(a.getPrecio())) {
					a.setPrecio(String.valueOf(DEFAULT_PRICE));
				}

				i = convertirDouble(a.getPrecio())
						.compareTo(convertirDouble(b.getPrecio()));
	
				estilizarPrecios(a,b);
				
			} else if(a.getOrdenacion() == 2) {
		
				if(Objects.isNull(a.getPrecioKilo()) || 
						StringUtils.isAllEmpty(a.getPrecioKilo())) {
					a.setPrecioKilo(String.valueOf(DEFAULT_PRICE));
				}

				mismoPrecioYPrecioKilo(a, b);
	
				i = convertirDouble(a.getPrecioKilo())
						.compareTo(convertirDouble(b.getPrecioKilo()));

				estilizarPrecios(a,b);
			}
		} else {
			UnsupportedAttributeException e = new UnsupportedAttributeException("Error en el parametro de ordenacion", String.valueOf(a.getOrdenacion()));
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
			throw e;
		}
		
		return i;
	}
	
	@Override
	public int compare(IFProcessPrice a, IFProcessPrice b) {
		return processPrice(a, b);
	}
		
	/**
	 * Extrae el valor númerico de la cadena y lo convierte en double.
	 * 
	 * @param strPrecioKilo
	 * @return Double
	 */
	private  Double convertirDouble(final String strPrecioKilo) {
		
		String strPrecioKiloRes = extraerDecimal(strPrecioKilo);
		
		if(StringUtils.isAllEmpty(strPrecioKiloRes)) {
			strPrecioKiloRes = extraerEntero(strPrecioKilo);
		}
 
		strPrecioKiloRes = strPrecioKiloRes.trim();
		return Double.parseDouble(strPrecioKiloRes);
	}
	
	/**
	 * Método que contiene el Algoritmo encargado de 
	 * extraer el valor decimal correspondiente al precio 
	 * de la cadena en la que va insertado. 
	 * 
	 * @param cadena
	 * @return String
	 */
	private  String extraerDecimal(@NotNull final String cadena) {
		
		if(COMMA_STRING.equals(cadena)) {
			return DEFAULT_STR_PRICE;
		}
	     
	  String resultado = StringUtils.EMPTY;
	  
	  String cadenaAux = cadena.replace(DOT_STRING, StringUtils.EMPTY);
	 
	  if(cadenaAux.contains(PIPE_STRING)) {
		  cadenaAux = cadenaAux.substring(
				  cadenaAux.lastIndexOf(PIPE_STRING)+1,
				  cadenaAux.length()).trim().
				  replaceAll(StringUtils.SPACE
						  .concat(StringUtils.SPACE),
						  StringUtils.SPACE);
	  }

	  Matcher mDecimal = Pattern.compile(
			  REGEX_NUMERO_DECIMAL, Pattern.MULTILINE).matcher(cadenaAux);

	  if (mDecimal.find()) {
	     resultado = mDecimal.group(0);
	  }

	  if(!StringUtils.isAllEmpty(resultado)) {	  
		  resultado = resultado.replace(COMMA_STRING, DOT_STRING);
	  } else {
		  return extraerEntero(cadenaAux);
	  }
	  
	  return resultado;
	}
	
	/**
	 * Método que contiene el Algoritmo encargado de 
	 * extraer el valor entero correspondiente al precio 
	 * de la cadena en la que va insertado. 
	 * 
	 * @param cadena
	 * @return String
	 */
	private  String extraerEntero(@NotNull final String cadena) {
		
		String resultado = StringUtils.EMPTY;		
		Matcher mEntero = Pattern.compile(REGEX_NUMERO_DECIMAL, Pattern.MULTILINE).matcher(cadena);
		
	  if(mEntero.find()) {
		  return DECIMALES;
	  }
		
	  String cadenaAux = cadena.replace(DOT_STRING, StringUtils.EMPTY);
		
	  mEntero = Pattern.compile(REGEX_NUMERO_DECIMAL, Pattern.MULTILINE).matcher(cadenaAux);
		
	    if(mEntero.find()) {
		   resultado = mEntero.group();
		   resultado = resultado.concat(DECIMALES);
	    }

		if(StringUtils.isAllEmpty(resultado)) {
			  mEntero = Pattern.compile(REGEX_INTEGER, Pattern.MULTILINE).matcher(cadenaAux);

			if(mEntero.find()) {
				  resultado = mEntero.group();
				  resultado = resultado.concat(DECIMALES);
			}
		}
		
		return resultado;
	}
	
	/**
	 * Si la variable de peso por kilo está vacía se le aplica
	 * el valor del precio por unidad.
	 * 
	 * @param a
	 * @param b
	 */
	private  void mismoPrecioYPrecioKilo(IFProcessPrice a, IFProcessPrice b) {
		
		if(StringUtils.isAllEmpty(a.getPrecioKilo()) &&
				!StringUtils.isAllEmpty(a.getPrecio())) {
			a.setPrecioKilo(extraerDecimal(a.getPrecio())
					.concat(BARRA_KILO_GRAM));
		}
		
		if(StringUtils.isAllEmpty(b.getPrecioKilo()) &&
				!StringUtils.isAllEmpty(b.getPrecio())) {
			b.setPrecioKilo(extraerDecimal(b.getPrecio())
					.concat(BARRA_KILO_GRAM));
		}
	}
	
	private  void estilizarPrecios(final IFProcessPrice a, final IFProcessPrice b) {
		
		if(!StringUtils.isAllEmpty(a.getPrecio()) &&
				!StringUtils.isAllEmpty(a.getPrecio())) {
		a.setPrecio(extraerDecimal(a.getPrecio())
				.concat(EURO_SYMBOL)
				.replace(DOT_STRING,COMMA_STRING));	
		
		b.setPrecio(extraerDecimal(b.getPrecio())
				.concat(EURO_SYMBOL)
				.replace(DOT_STRING,COMMA_STRING));
		}
		
		if(!StringUtils.isAllEmpty(a.getPrecioKilo()) &&
				!StringUtils.isAllEmpty(a.getPrecioKilo())) {
				a.setPrecioKilo(extraerDecimal(a.getPrecioKilo())
				.concat(BARRA_KILO_GRAM)
				.replace(DOT_STRING,COMMA_STRING));	
		
				b.setPrecioKilo(extraerDecimal(b.getPrecioKilo())
				.concat(BARRA_KILO_GRAM)
				.replace(DOT_STRING,COMMA_STRING));	
		}
	}
	
	public List<IFProcessPrice> ordenarLista(List<IFProcessPrice> lista) {
		Collections.sort(lista, this);
		return lista;
	}
}
