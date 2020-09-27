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

	private Integer did;
	private Boolean bolActivo;
	private String desUrl;
	private String nomUrl;
	private Integer didEmpresa;
	private String nomEmpresa;
	private Boolean bolStatus;	
	private Boolean bolLogin;	
	
	private CssSelectorsDto selectores;
	
}
