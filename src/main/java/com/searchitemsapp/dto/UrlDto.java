package com.searchitemsapp.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Component
public class UrlDto {

	private Long did;
	private Boolean bolActivo;
	private String desUrl;
	private String nomUrl;
	private Long didEmpresa;
	private String nomEmpresa;
	private Boolean bolStatus;	
	private Boolean bolLogin;	
	
	private CssSelectorsDto selectores;
	
}
