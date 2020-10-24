package com.searchitemsapp.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Component
public class ProductDto {
	
	private int identificador;
	private String nomProducto;
	private String desProducto;
	private Long didEmpresa;
	private String nomEmpresa;
	private String precioKilo;
	private String imagen;
	private String precio;
	private String nomUrl;
	private volatile Integer didUrl;
	private volatile Boolean bolActivo;
	private volatile Boolean bolStatus;
	private volatile Boolean bolLogin;	
	private String desUrl;
	private String nomUrlAllProducts;
	private Integer ordenacion;
	
	
}
