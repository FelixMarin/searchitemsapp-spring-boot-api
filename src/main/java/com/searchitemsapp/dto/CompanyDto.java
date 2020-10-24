package com.searchitemsapp.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Component
public class CompanyDto {

	private Long did;
	private Boolean bolActivo;
	private String desEmpresa;
	private String nomEmpresa;
	private Boolean bolDynScrap;	
	private Long didCatEmpresa;
	private String nomCatEmpresa;
	private Long didPais;
	private String nomPais;
	private CssSelectorsDto selectores;
	private UrlDto urls;
}
