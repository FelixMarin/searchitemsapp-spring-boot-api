package com.searchitemsapp.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Component
public class CategoriaDTO {

	private Integer did;
	private Boolean bolActivo;
	private String desCatEmpresa;
	private String nomCatEmpresa;	
	private EmpresaDTO empresas;
	private MarcasDTO marcas;
	private NomProductoDTO productos;

}
