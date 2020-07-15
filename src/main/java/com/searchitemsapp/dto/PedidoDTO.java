package com.searchitemsapp.dto;

import org.springframework.stereotype.Component;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * Objeto de Transferencia de Datos (DTO) 
 * es un objeto que transporta datos entre procesos.
 * No tiene más comportamiento que almacenar y entregar 
 * sus propios datos.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Data @NoArgsConstructor @AllArgsConstructor
@Component
public class PedidoDTO implements IFdto {
	
	@NotNull
	private String categoria;
	private String producto;

}
