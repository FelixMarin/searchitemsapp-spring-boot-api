package com.searchitemsapp.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Component
public class ProductDto {
	
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
	private Boolean bolActivo;
	private Boolean bolStatus;
	private Boolean bolLogin;	
	private String desUrl;
	private String nomUrlAllProducts;
	private Integer ordenacion;
}
