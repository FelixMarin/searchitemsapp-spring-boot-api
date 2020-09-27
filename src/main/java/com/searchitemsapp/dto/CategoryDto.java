package com.searchitemsapp.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Component
public class CategoryDto {

	private Integer did;
	private Boolean bolActivo;
	private String desCatEmpresa;
	private String nomCatEmpresa;	
	private EnterpriseDto empresas;
	private BrandsDto marcas;
	private LiveSearchDto productos;

}
