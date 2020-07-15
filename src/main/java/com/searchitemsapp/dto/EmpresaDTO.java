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
public class EmpresaDTO implements IFdto {

	@NotNull
	private Integer did;
	private Boolean bolActivo;
	private String desEmpresa;
	private String nomEmpresa;
	private Boolean bolDynScrap;	
	
	private Integer didCatEmpresa;
	private String nomCatEmpresa;
	private Integer didPais;
	private String nomPais;
	private SelectoresCssDTO selectores;
	private UrlDTO urls;

}
