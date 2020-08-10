package com.searchitemsapp.processdata;

import java.util.Comparator;
import java.util.List;

public abstract interface ProcessPrice extends Comparator<ProcessPrice>{
	abstract int getIdentificador();
	abstract void setIdentificador(int identificador);
	abstract String getNomProducto();
	abstract void setNomProducto(String nomProducto);
	abstract String getDesProducto();
	abstract void setDesProducto(String desProducto);
	abstract String getImagen();
	abstract void setImagen(String imagen);
	abstract String getPrecio();
	abstract void setPrecio(String precio);
	abstract Integer getDidEmpresa();
	abstract void setDidEmpresa(Integer didEmpresa);
	abstract String getPrecioKilo();
	abstract void setPrecioKilo(String precioKilo);
	abstract String getNomUrl();
	abstract void setNomUrl(String nomUrl);
	abstract Integer getDidUrl();
	abstract void setDidUrl(Integer didUrl);
	abstract boolean isBolActivo();
	abstract void setBolActivo(boolean bolActivo);
	abstract boolean isBolStatus();
	abstract void setBolStatus(boolean bolStatus);
	abstract String getDesUrl();
	abstract void setDesUrl(String desUrl);
	abstract String getNomUrlAllProducts();
	abstract void setNomUrlAllProducts(String nomUrlAllProducts);
	abstract int getOrdenacion();
	abstract void setOrdenacion(int ordenacion);
	abstract String getNomEmpresa();
	abstract void setNomEmpresa(String nomEmpresa);
	abstract int processPrice(final ProcessPrice a, final ProcessPrice b);
	abstract int compare(ProcessPrice a, ProcessPrice b);
	abstract List<ProcessPrice> ordenarLista(List<ProcessPrice> lista);
	abstract String toString();
}
