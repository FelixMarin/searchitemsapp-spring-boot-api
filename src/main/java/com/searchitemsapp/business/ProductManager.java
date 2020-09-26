package com.searchitemsapp.business;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Element;

import com.searchitemsapp.dto.BrandsDto;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.UrlDto;

import lombok.NonNull;

public interface ProductManager {
	
	abstract boolean checkProduct(final String requestProducName, final int iIdEmpresa, 
			final ProductDto productoDto, PatternsManager elementPatterns, 
			final List<BrandsDto> listTodasMarcas);
	
	abstract String eliminarTildes(final String cadena);
	
	abstract String tratarProducto(final String producto) throws IOException;
	
	abstract ProductDto addElementsToProducts(@NonNull Element elem,
			@NonNull UrlDto urlDto, 
			@NonNull String ordenacion) throws IOException;
}
