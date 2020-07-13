package com.searchitemsapp.processdata;

import java.util.Comparator;
import java.util.List;

public abstract interface IFProcessPrice extends Comparator<IFProcessPrice>{
	public abstract int getIdentificador();
	public abstract void setIdentificador(int identificador);
	public abstract String getNomProducto();
	public abstract void setNomProducto(String nomProducto);
	public abstract String getDesProducto();
	public abstract void setDesProducto(String desProducto);
	public abstract String getImagen();
	public abstract void setImagen(String imagen);
	public abstract String getPrecio();
	public abstract void setPrecio(String precio);
	public abstract Integer getDidEmpresa();
	public abstract void setDidEmpresa(Integer didEmpresa);
	public abstract String getPrecioKilo();
	public abstract void setPrecioKilo(String precioKilo);
	public abstract String getNomUrl();
	public abstract void setNomUrl(String nomUrl);
	public abstract Integer getDidUrl();
	public abstract void setDidUrl(Integer didUrl);
	public abstract boolean isBolActivo();
	public abstract void setBolActivo(boolean bolActivo);
	public abstract boolean isBolStatus();
	public abstract void setBolStatus(boolean bolStatus);
	public abstract String getDesUrl();
	public abstract void setDesUrl(String desUrl);
	public abstract String getNomUrlAllProducts();
	public abstract void setNomUrlAllProducts(String nomUrlAllProducts);
	public abstract int getOrdenacion();
	public abstract void setOrdenacion(int ordenacion);
	public abstract String getNomEmpresa();
	public abstract void setNomEmpresa(String nomEmpresa);
	public abstract int processPrice(final IFProcessPrice a, final IFProcessPrice b);
	public abstract int compare(IFProcessPrice a, IFProcessPrice b);
	public abstract List<IFProcessPrice> ordenarLista(List<IFProcessPrice> lista);
	public abstract String toString();
}
