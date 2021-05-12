package com.searchitemsapp.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Component
public class CountryDto {
	
	private Long did;
	private Boolean bolActivo;
	private String desPais;
	private String nomPais;
	private CompanyDto empresas;
	private BrandsDto marcas;
	private LiveSearchDto productos;
	
}
