package com.searchitemsapp.business.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.Prices;
import com.searchitemsapp.dto.ProductDto;

@RunWith(SpringRunner.class)
@SpringBootTest
class PricesImplTest {
	
	@Autowired
	private Prices price;

	@Test
	void testSortProductsByPrice() {
		List<ProductDto> products = Lists.newArrayList();
		ProductDto product1 = ProductDto.builder().ordenacion(1).precio("1.00|").build();
		ProductDto product2 = ProductDto.builder().ordenacion(1).precio("2.00").build();
		products.add(product1);
		products.add(product2);		
		List<ProductDto> productsResult = price.sortProductsByPrice(products);
		assertNotNull(productsResult);
		assertEquals(2, productsResult.size());
	}

	@Test
	void testCompare() {
		ProductDto product1 = ProductDto.builder().ordenacion(1).precio("1.00").build();
		ProductDto product2 = ProductDto.builder().ordenacion(1).precio("0.50").build();		
		int res = price.compare(product1, product2);
		assertEquals(1, res);
	}
 
	@Test
	void testPriceComparator() {
		ProductDto product1 = ProductDto.builder().ordenacion(1).precio("0.50").build();
		ProductDto product2 = ProductDto.builder().ordenacion(1).precio("0.50").build();
		int res = price.priceComparator(product1, product2);
		assertEquals(0, res);
		
		product1 = ProductDto.builder().ordenacion(2).precio("1").precioKilo("1").build();
		product2 = ProductDto.builder().ordenacion(2).precio("1").precioKilo("1").build();
		res = price.priceComparator(product1, product2);
		assertEquals(0, res);
		
		product1 = ProductDto.builder().ordenacion(2).precio("1,00").build();
		product2 = ProductDto.builder().ordenacion(2).precio("1,00").build();
		res = price.priceComparator(product1, product2);
		assertEquals(1, res);
		
		product1 = ProductDto.builder().ordenacion(1).precio(",").build();
		product2 = ProductDto.builder().ordenacion(1).precio(",").build();
		res = price.priceComparator(product1, product2);
		assertEquals(0, res);
		
		product1 = ProductDto.builder().ordenacion(1).precio(",00").build();
		product2 = ProductDto.builder().ordenacion(1).precio(",00").build();
		res = price.priceComparator(product1, product2);
		assertEquals(0, res);
	}
	
	@Test
	void testpriceComparatorException() {
	 
	    assertThrows(NumberFormatException.class, () -> {
	    	ProductDto product1 = ProductDto.builder().ordenacion(1).precio("").build();
	    	ProductDto product2 = ProductDto.builder().ordenacion(1).precio("").build();
			price.priceComparator(product1, product2);
	    });
	}
}
