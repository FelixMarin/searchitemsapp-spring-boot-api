package com.searchitemsapp.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Component
public class CssSelectorsDto {
	
	private Long did;
	private Boolean bolActivo;
	private LocalDate fecModificacion;
	private String scrapNoPattern;
	private String scrapPattern;
	private String selImage;
	private String selLinkProd;
	private String selPreKilo;
	private String selPrecio;
	private String selProducto;
	private String selPaginacion;	
	private Long didEmpresa;
	private String nomEmpresa;	
	private Long didUrl;
	private String nomUrl;

}
