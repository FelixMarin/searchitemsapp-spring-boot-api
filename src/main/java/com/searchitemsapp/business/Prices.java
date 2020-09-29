package com.searchitemsapp.business;

import java.util.Comparator;
import java.util.List;

import com.searchitemsapp.dto.ProductDto;

public interface Prices extends Comparator<ProductDto>{
	
	abstract int priceComparator(final ProductDto primaryPrice, final ProductDto secondaryProduct);
	abstract int compare(ProductDto primaryPrice, ProductDto secondaryPrice);
	abstract List<ProductDto> sortProductsByPrice(List<ProductDto> productos);
	abstract String toString();
}
