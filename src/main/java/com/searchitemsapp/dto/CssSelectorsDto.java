package com.searchitemsapp.dto;

import java.time.LocalDate;

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
public class CssSelectorsDto {
	
	private Integer did;
	private Boolean bolActivo;
	private LocalDate fecModificacion;
	private String scrapNoPattern;
	private String scrapPattern;
	private String selImage;
	private String selLinkProd;
	private String selPreKilo;
	private String selPrecio;
	private String selProducto;
	private String selPaginacion;	
	private Integer didEmpresa;
	private String nomEmpresa;	
	private Integer didUrl;
	private String nomUrl;

}
