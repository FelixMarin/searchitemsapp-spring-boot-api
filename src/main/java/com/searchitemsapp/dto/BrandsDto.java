package com.searchitemsapp.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Component
public class BrandsDto {

	private Long did;
	private String nomMarca;
	private Long didCatEmpresas;
	private String nomCatEmpresas;	
	private Long didPais;
	private String nomPais;
	
}
