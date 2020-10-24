package com.searchitemsapp.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Component
public class LiveSearchDto {

	private Long did;
	private String nomProducto;
	
}
