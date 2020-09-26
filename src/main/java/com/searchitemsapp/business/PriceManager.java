package com.searchitemsapp.business;

import java.util.Comparator;
import java.util.List;

import com.searchitemsapp.dto.ProductDto;

public abstract interface PriceManager extends Comparator<ProductDto>{
	
	abstract int priceComparator(final ProductDto a, final ProductDto b);
	abstract int compare(ProductDto a, ProductDto b);
	abstract List<ProductDto> ordenarLista(List<ProductDto> lista);
	abstract String toString();
}
