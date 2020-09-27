package com.searchitemsapp.business.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.searchitemsapp.business.Brands;
import com.searchitemsapp.business.Patterns;
import com.searchitemsapp.business.Products;
import com.searchitemsapp.business.SelectorsCss;
import com.searchitemsapp.business.enterprises.Enterprise;
import com.searchitemsapp.business.enterprises.factory.EnterpriseFactory;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.UrlDto;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Component
@AllArgsConstructor
public class ProductsImpl implements Products {
	
	private SelectorsCss cssSelectors;
	private EnterpriseFactory enterpriseFactory;
	
	@Override
	public boolean checkProduct(String requestProducName, int enterpriseId, 
			ProductDto productDto, Patterns elementPatterns, 
			Brands brands) throws IOException {
			
		String productName = brands.brandFilter(enterpriseId, productDto.getNomProducto(), brands.allBrandList());
		
		if(StringUtils.isAllBlank(productName)) {
			return Boolean.FALSE;
		}
		
		String[] productCharArray = requestProducName.split(StringUtils.SPACE);
		Pattern patternProduct = elementPatterns.createPatternProduct(productCharArray);
		
		return patternProduct.matcher(removeTildes(productName).toUpperCase()).find();
	}
	
	@Override
	public String removeTildes(final String accentedWord) {
		
		if(accentedWord.indexOf('\u00f1') != -1) {
			return accentedWord;
		}
		
		String wordWithoutTildes = accentedWord.replace("Ñ", "u00f1");
		wordWithoutTildes = Normalizer.normalize(wordWithoutTildes.toLowerCase(), Normalizer.Form.NFD);
		wordWithoutTildes = wordWithoutTildes.replaceAll("[\\p{InCombiningDiacriticalMarks}]", StringUtils.EMPTY);
		wordWithoutTildes = wordWithoutTildes.replace("u00f1",  "ñ");
		return Normalizer.normalize(wordWithoutTildes, Normalizer.Form.NFC);
	}
	
	@Override
	public ProductDto addElementsToProducts(@NonNull Element elem,
			@NonNull UrlDto urlDto, 
			@NonNull String ordenacion) throws IOException {

		ProductDto productDto = ProductDto.builder()
				.imagen(cssSelectors.elementByCssSelector(elem, urlDto.getSelectores().getSelImage(), urlDto))
				.nomProducto(cssSelectors.elementByCssSelector(elem, urlDto.getSelectores().getSelProducto(), urlDto))
				.desProducto(cssSelectors.elementByCssSelector(elem, urlDto.getSelectores().getSelProducto(), urlDto))
				.precio(cssSelectors.elementByCssSelector(elem, urlDto.getSelectores().getSelPrecio(), urlDto))
				.precioKilo(cssSelectors.elementByCssSelector(elem, urlDto.getSelectores().getSelPreKilo(), urlDto))
				.nomUrl(cssSelectors.elementByCssSelector(elem, urlDto.getSelectores().getSelLinkProd(), urlDto))
				.didEmpresa(urlDto.getDidEmpresa())
				.nomEmpresa(urlDto.getNomEmpresa())
				.ordenacion(Integer.parseInt(ordenacion))
				.build();
		
		Enterprise enterprise = enterpriseFactory.getInstance(urlDto.getDidEmpresa());
		String namesOfUrlsOfAllProducts = enterprise.getAllUrlsToSearch(productDto);
		productDto.setNomUrlAllProducts(namesOfUrlsOfAllProducts);
			
		return productDto;
	}
	
	@Override
	public String manageProductName(final String productName) throws IOException {
		
		String tratedProduct=addCharacters(productName, Character.MIN_VALUE, 0, 0);
		tratedProduct=addCharacters(tratedProduct, Character.MIN_VALUE, 0, 1);
		
		Matcher matcher = Pattern.compile("(\\%|\\$00)").matcher(tratedProduct);
		
		if(matcher.find()) {
			return tratedProduct;
		} else {
			return URLEncoder.encode(tratedProduct, StandardCharsets.UTF_8.toString());
		}
	}
	
	private String addCharacters(@NonNull String productName, char charToAppend, int size, int type) {

		StringBuilder stringBuilder = new StringBuilder(10);
		
		if (type == 0) {
			stringBuilder.append(productName);
		}
		
		for (int i = 0; i < (size - productName.length()); i++) {
			stringBuilder.append(charToAppend);
		}

		if (type == 1) {
			stringBuilder.append(productName);
		}

		return stringBuilder.toString();
	}
}
