package com.searchitemsapp.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




/**
 * 
 * @author Felix Marin Ramirez
 *
 */
@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Component
public class OrderDto {
	
	private String categoria;
	private String producto;

}
