package com.searchitemsapp.business.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.Prices;
import com.searchitemsapp.dto.ProductDto;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class PricesImplTest {
	
	@Autowired
	private Prices price;

	@Test
	void testSortProductsByPrice() {
		List<ProductDto> products = Lists.newArrayList();
		var product1 = ProductDto.builder().ordenacion(1).precio("1.00|").build();
		var product2 = ProductDto.builder().ordenacion(1).precio("2.00").build();
		products.add(product1);
		products.add(product2);		
		List<ProductDto> productsResult = price.sortProductsByPrice(products);
		assertNotNull(productsResult);
		assertEquals(2, productsResult.size());
	}

	@Test
	void testCompare() {
		var product1 = ProductDto.builder().ordenacion(1).precio("1.00").build();
		var product2 = ProductDto.builder().ordenacion(1).precio("0.50").build();		
		int res = price.compare(product1, product2);
		assertEquals(1, res);
	}
 
	@Test
	void testPriceComparator() {
		var product1 = ProductDto.builder().ordenacion(1).precio("0.50").build();
		var product2 = ProductDto.builder().ordenacion(1).precio("0.50").build();
		var res = price.priceComparator(product1, product2);
		assertEquals(0, res);
		
		product1 = ProductDto.builder().ordenacion(2).precio("1").precioKilo("1").build();
		product2 = ProductDto.builder().ordenacion(2).precio("1").precioKilo("1").build();
		res = price.priceComparator(product1, product2);
		assertEquals(0, res);
		
		product1 = ProductDto.builder().ordenacion(2).precio("1,00").build();
		product2 = ProductDto.builder().ordenacion(2).precio("1,00").precioKilo("1,00").build();
		res = price.priceComparator(product1, product2);
		assertEquals(0, res);
		
		product1 = ProductDto.builder().ordenacion(1).precio(",").build();
		product2 = ProductDto.builder().ordenacion(1).precio(",").build();
		res = price.priceComparator(product1, product2);
		assertEquals(0, res);
		
		product1 = ProductDto.builder().ordenacion(1).precio(",00").build();
		product2 = ProductDto.builder().ordenacion(1).precio(",00").build();
		res = price.priceComparator(product1, product2);
		assertEquals(0, res);
		
		product1 = ProductDto.builder().ordenacion(1).precio("").build();
		product2 = ProductDto.builder().ordenacion(1).precio("").build();
		res = price.priceComparator(product1, product2);
		assertEquals(0.0, res);
		
		product1 = ProductDto.builder().ordenacion(1).precio("A").build();
		product2 = ProductDto.builder().ordenacion(1).precio("B").build();
		res = price.priceComparator(product1, product2);
		assertEquals(0.0, res);
		
		product1 = ProductDto.builder().ordenacion(1).precio("").build();
		product2 = ProductDto.builder().ordenacion(1).precio("").build();
		res = price.priceComparator(product1, product2);
		assertEquals(0.0, res);
	}
}
