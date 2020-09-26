package com.searchitemsapp.business.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.searchitemsapp.business.BrandsManager;
import com.searchitemsapp.business.PatternsManager;
import com.searchitemsapp.business.ProductManager;
import com.searchitemsapp.business.SelectorCssManager;
import com.searchitemsapp.business.enterprises.Enterprise;
import com.searchitemsapp.business.enterprises.factory.EnterpriseFactory;
import com.searchitemsapp.dto.BrandsDto;
import com.searchitemsapp.dto.ProductDto;
import com.searchitemsapp.dto.UrlDto;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Component
@AllArgsConstructor
public class ProductManagerImpl implements ProductManager {
	
	private BrandsManager brandManager;
	private SelectorCssManager selectorCssManager;
	private EnterpriseFactory entityFactory;
	
	@Override
	public boolean checkProduct(final String requestProducName, final int enterpriseId, 
			final ProductDto productDto, PatternsManager elementPatterns, 
			final List<BrandsDto> listAllBrands) {
			
		String strProducto = brandManager.filtroMarca(enterpriseId, productDto.getNomProducto(), listAllBrands);
		
		if(StringUtils.isAllBlank(strProducto)) {
			return Boolean.FALSE;
		}
		
		String[] productCharArray = requestProducName.split(StringUtils.SPACE);
		Pattern patternProducto = elementPatterns.createPatternProduct(productCharArray);
		
		return patternProducto.matcher(eliminarTildes(strProducto).toUpperCase()).find();
	}
	
	@Override
	public String eliminarTildes(final String cadena) {
		
		if(cadena.indexOf('\u00f1') != -1) {
			return cadena;
		}
		
		String resultado = cadena.replace("Ñ", "u00f1");
		resultado = Normalizer.normalize(resultado.toLowerCase(), Normalizer.Form.NFD);
		resultado = resultado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", StringUtils.EMPTY);
		resultado = resultado.replace("u00f1",  "ñ");
		return Normalizer.normalize(resultado, Normalizer.Form.NFC);
	}
	
	@Override
	public String tratarProducto(final String producto) throws IOException {
		
		String productoTratado=anadirCaracteres(producto, Character.MIN_VALUE, 0, 0);
		productoTratado=anadirCaracteres(productoTratado, Character.MIN_VALUE, 0, 1);
		
		Matcher matcher = Pattern.compile("(\\%|\\$00)").matcher(productoTratado);
		
		if(matcher.find()) {
			return productoTratado;
		} else {
			return URLEncoder.encode(productoTratado, StandardCharsets.UTF_8.toString());
		}
	}
	
	private String anadirCaracteres(@NonNull String strCadena, char chrCaracter, int iLongitud, int iTipo) {

		StringBuilder stringBuilder = new StringBuilder(10);
		
		if (iTipo == 0) {
			stringBuilder.append(strCadena);
		}
		
		for (int i = 0; i < (iLongitud - strCadena.length()); i++) {
			stringBuilder.append(chrCaracter);
		}

		if (iTipo == 1) {
			stringBuilder.append(strCadena);
		}

		return stringBuilder.toString();
	}
	
	public ProductDto addElementsToProducts(@NonNull Element elem,
			@NonNull UrlDto urlDto, 
			@NonNull String ordenacion) throws IOException {

		int currentEnterpriseId = urlDto.getDidEmpresa();
		ProductDto productoDto = new ProductDto(); 
		Enterprise enterprise = entityFactory.getEnterpriseData(currentEnterpriseId);

		productoDto.setImagen(selectorCssManager.elementoPorCssSelector(elem, urlDto.getSelectores().getSelImage(), urlDto));
		productoDto.setNomProducto(selectorCssManager.elementoPorCssSelector(elem, urlDto.getSelectores().getSelProducto(), urlDto));
		productoDto.setDesProducto(selectorCssManager.elementoPorCssSelector(elem, urlDto.getSelectores().getSelProducto(), urlDto));
		productoDto.setPrecio(selectorCssManager.elementoPorCssSelector(elem, urlDto.getSelectores().getSelPrecio(), urlDto));
		productoDto.setPrecioKilo(selectorCssManager.elementoPorCssSelector(elem, urlDto.getSelectores().getSelPreKilo(), urlDto));
		productoDto.setNomUrl(selectorCssManager.elementoPorCssSelector(elem, urlDto.getSelectores().getSelLinkProd(), urlDto));
		productoDto.setDidEmpresa(urlDto.getDidEmpresa());
		productoDto.setNomEmpresa(urlDto.getNomEmpresa());
		productoDto.setNomUrlAllProducts(enterprise.getAllUrlsToSearch(productoDto));		
		productoDto.setOrdenacion(Integer.parseInt(ordenacion));		
		
		return productoDto;
	}
}
