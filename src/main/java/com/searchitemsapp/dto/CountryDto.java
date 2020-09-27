package com.searchitemsapp.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Component
public class CountryDto {
	
	private Integer did;
	private Boolean bolActivo;
	private String desPais;
	private String nomPais;
	private EnterpriseDto empresas;
	private BrandsDto marcas;
	private LiveSearchDto productos;
	
}
