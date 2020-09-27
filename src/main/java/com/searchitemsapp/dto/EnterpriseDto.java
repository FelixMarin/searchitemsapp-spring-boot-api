package com.searchitemsapp.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Component
public class EnterpriseDto {

	private Integer did;
	private Boolean bolActivo;
	private String desEmpresa;
	private String nomEmpresa;
	private Boolean bolDynScrap;	
	private Integer didCatEmpresa;
	private String nomCatEmpresa;
	private Integer didPais;
	private String nomPais;
	private CssSelectorsDto selectores;
	private UrlDto urls;
}
