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
	
	private static final transient Logger LOGGER = LoggerFactory.getLogger(ProcessPriceModule.class); 
	
	private static final transient String DECIMALES = ".00";
	private static final transient String DEFAULTSTRPRICE = "1000.00";
	private static final transient String REGEXNUMERODECIMAL = "(\\d*[,][0-9]*)|([0-9]{1,9})";
	private static final transient String REGEXINTEGER = "\\d+";
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
	
	public ProcessPriceModule() {
		super();
	}

	public List<IFProcessPrice> ordenarLista(List<IFProcessPrice> lista) {
		lista.sort(this);
		return lista;
	}
	
	@Override
	public int compare(IFProcessPrice precioPrimario, IFProcessPrice precioSecundario) {
		return processPrice(precioPrimario, precioSecundario);
	}
	
	public int processPrice(final IFProcessPrice precioPrimario, final IFProcessPrice precioSecundario) {
		
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
			return DEFAULTSTRPRICE;
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
			  mEntero = Pattern.compile(REGEXINTEGER, Pattern.MULTILINE).matcher(cadenaAux);

			if(mEntero.find()) {
				  strResultado = mEntero.group();
				  strResultado = strResultado.concat(DECIMALES);
			}
		}
		
		return strResultado;
	}
	
	private  void mismoPrecioYPrecioKilo(IFProcessPrice precioPrimario, IFProcessPrice precioSecundario) {
		
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

	private  void estilizarPrecios(final IFProcessPrice precioPrimario, final IFProcessPrice precioSecundario) {
		
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
}
