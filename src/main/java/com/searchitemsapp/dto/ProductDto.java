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
	private Integer didEmpresa;
	private String nomEmpresa;
	private String precioKilo;
	private String imagen;
	private String precio;
	private String nomUrl;
	private volatile transient Integer didUrl;
	private volatile transient Boolean bolActivo;
	private volatile transient Boolean bolStatus;
	private volatile transient Boolean bolLogin;	
	private String desUrl;
	private String nomUrlAllProducts;
	private Integer ordenacion;
	
	
}
