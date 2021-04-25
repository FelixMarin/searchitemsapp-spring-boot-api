package com.searchitemsapp.business.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.business.Patterns;
import com.searchitemsapp.business.Products;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.exception.ResourceNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductsImplTest {
	
	@Autowired
	private Products product;
	
	@Autowired 
	private Patterns patterns;

	@Test
	void testCheckProduct() throws IOException, ResourceNotFoundException {
		
		String melon = "melón";
		String sandia = "sandia";
		
		Optional<ProductDto> optional = product.checkProduct(sandia, 101l, 
				ProductDto.builder().nomProducto(sandia).build(), patterns);
		
		assertNotNull(optional);
		assertTrue(optional.isPresent());
		
		optional = product.checkProduct(melon, 101l, 
				ProductDto.builder().nomProducto(melon).build(), patterns);
		
		assertNotNull(optional);
		assertTrue(optional.isEmpty());
	}

	@Test
	void testRemoveTildes() {
		String expected = "melon";
		String search = "melón";
		String actual = product.removeTildes(search);
		assertEquals(actual, expected);
		
		expected = "españa";
		search = "españa";
		actual = product.removeTildes(search);
		assertEquals(actual, expected);
	}

	@Test
	void testAddElementsToProducts() throws IOException {
		
		final String baseUri = "https://www.dia.es/compra-online/search?q=miel%3Aprice-asc&page=0&disp=";
		
		Document document = Document.createShell(baseUri);
		Element element = document.getAllElements().first();
		
		CssSelectorsDto selectoresDto = CssSelectorsDto.builder()
				.selImage("test")
				.selLinkProd("test")
				.selPaginacion("1")
				.selPrecio("test")
				.selPreKilo("test")
				.selProducto("test")
				.build();
		
		UrlDto urlDto = UrlDto.builder()
				.selectores(selectoresDto)
				.didEmpresa(105L)
				.nomUrl(baseUri)
				.build();
		
		ProductDto producto = product.addElementsToProducts(element, urlDto, "1");
		assertNotNull(producto);
	}

	@Test
	void testManageProductName() throws IOException {
		String  res = product.manageProductName(" left");
		assertEquals("+left", res);
		res = product.manageProductName("right ");
		assertEquals("right+", res);
		res = product.manageProductName("inside blank");
		assertEquals("inside+blank", res);
		res = product.manageProductName("$ inside$blank$");
		assertEquals("%24+inside%24blank%24", res);
		res = product.manageProductName("\\% taged");
		assertEquals("\\% taged", res);
		
	}

}
