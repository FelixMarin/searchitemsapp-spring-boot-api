package com.searchitemsapp.dto;

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
public class PedidoDTO implements IFdto {
	
	private String categoria;
	private String producto;

	public PedidoDTO() {
		super();
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		return "PedidoDTO [categoria=" + categoria + ", producto=" + producto + "]";
	}
	
}
