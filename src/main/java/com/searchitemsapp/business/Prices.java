package com.searchitemsapp.business;

import java.util.Comparator;
import java.util.List;

import com.searchitemsapp.dto.ProductDto;

public interface Prices extends Comparator<ProductDto>{
	
	abstract int priceComparator(final ProductDto a, final ProductDto b);
	abstract int compare(ProductDto a, ProductDto b);
	abstract List<ProductDto> sortList(List<ProductDto> lista);
	abstract String toString();
}
