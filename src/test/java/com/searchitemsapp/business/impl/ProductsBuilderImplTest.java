package com.searchitemsapp.business.impl;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.business.Documents;
import com.searchitemsapp.business.Patterns;
import com.searchitemsapp.business.ProductBuilder;
import com.searchitemsapp.business.Products;
import com.searchitemsapp.business.SelectorsCss;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.SearchItemsParamsDto;
import com.searchitemsapp.dto.UrlDto;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductsBuilderImplTest {
	
	@Autowired
	private ProductBuilder builder;
		
	@Autowired 
	private SelectorsCss selectorCss;
	
	@Autowired
	private Documents documents;
	
	@Autowired
	private Patterns patterns;
	
	@Autowired
	private Products products;
	
	@Autowired
	private SearchItemsParamsDto productsInParametersDto;
	
	@Autowired
	private UrlDto urlDto;

	@Test
	void testCall() throws IOException, URISyntaxException, InterruptedException, JSONException {
		builder.setCssSelectors(selectorCss);
		builder.setDocuments(documents);
		builder.setPatterns(patterns);
		builder.setProducts(products);
		urlDto.setDidEmpresa(105l);
		CssSelectorsDto cssSelector = CssSelectorsDto.builder().build();
		cssSelector.setSelPaginacion(".paginatorBottom li:nth-of-type(3) a|href");
		cssSelector.setScrapPattern("div.space");
		urlDto.setSelectores(cssSelector);
		urlDto.setNomUrl("https://www.dia.es/compra-online/search?q=sal%3Aprice-asc&page=0&disp=");
		builder.setUrlDto(urlDto);
		productsInParametersDto.setCategoryId("101");
		productsInParametersDto.setCountryId("101");
		productsInParametersDto.setPipedEnterprises("101");
		productsInParametersDto.setProduct("miel");
		productsInParametersDto.setSort("1");		
		builder.setProductsInParametersDto(productsInParametersDto);
		List<ProductDto> productos = builder.call();
		
		assertNotNull(productos);		
	}
}
