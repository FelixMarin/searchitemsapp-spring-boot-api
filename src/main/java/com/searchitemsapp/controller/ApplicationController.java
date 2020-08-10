package com.searchitemsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.searchitemsapp.dto.NomProductoDTO;
import com.searchitemsapp.services.ApplicationService;
import com.searchitemsapp.services.LiveSearchService;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@RestController
public class ApplicationController {
	
	@Autowired
	private ApplicationService ifApplicationService;
	
	@Autowired
	LiveSearchService lvs;
	
	@Autowired
	private ListaProductosValidator validator;
	
	@GetMapping(value = "/search", produces={MediaType.APPLICATION_JSON_VALUE})
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public @ResponseBody String listaProductos(@RequestBody 
				@RequestParam(value = "pais", defaultValue = "101") String didPais,
				@RequestParam(value = "categoria", defaultValue = "101") String didCategoria,
				@RequestParam(value = "ordenacion", defaultValue = "1") String ordenacion, 
				@RequestParam(value = "producto") @Validated String producto, 
				@RequestParam(value = "empresas") @Validated String empresas) {
	
		boolean isParams = validator.isEmpresa(empresas) &&	
		validator.isOrdenacion(ordenacion) &&	
		validator.isNumeric(didPais, didCategoria) &&
		validator.isParams(didPais, didCategoria, ordenacion, producto, empresas);
		
		if(isParams) {
		
			return ifApplicationService.service(didPais, didCategoria, 
							ordenacion, producto, empresas);
		} else {

			return "[{\"request\": \"Error\", "
					+ "\"id\" : \"-1\", "
					+ "\"description\": \"Invalid Input Data\"}]";
		}
	}
	
	@RequestMapping(value = "/product/{producto}", method = RequestMethod.GET)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<List<NomProductoDTO>> getProducto(@PathVariable("producto") final String prod) {
		return ResponseEntity.ok(lvs.buscarProducto(prod));
	}
}
