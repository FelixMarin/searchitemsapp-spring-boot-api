package com.searchitemsapp.processdata;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.dto.UrlDTO;
import com.searchitemsapp.processdata.empresas.IFProcessDataCondis;
import com.searchitemsapp.processdata.empresas.IFProcessDataDIA;
import com.searchitemsapp.processdata.empresas.IFProcessDataECI;
import com.searchitemsapp.processdata.empresas.IFProcessDataEmpresasFactory;
import com.searchitemsapp.processdata.empresas.IFProcessDataHipercor;
import com.searchitemsapp.processdata.empresas.IFProcessDataMercadona;

import lombok.NonNull;

@Component
public abstract class ProcessDataAbstract {
	
	private static final String PROTOCOL_ACCESSOR ="://";
	private static final String UNICODE_ENIE = "u00f1";	
	private static final String LEFT_PARENTHESIS_0 = " (";
	
	@Autowired 
	private ProcessDataDynamic procesDataDynamic;
	
	@Autowired
	private IFProcessDataEmpresasFactory processDataEmpresasFactory;
	
	@Autowired
	private IFProcessDataMercadona ifProcessDataMercadona;
				
	@Autowired
	private IFProcessDataCondis ifProcessDataCondis;
	
	@Autowired
	private IFProcessDataECI iFProcessDataECI;
	
	@Autowired
	private IFProcessDataHipercor iFProcessDataHipercor;
	
	@Autowired
	private IFProcessDataDIA iFProcessDataDIA;
	
	@Autowired
	private Environment env;
	
	protected List<Document> getHtmlDocument(final UrlDTO urlDto, 
			final Map<String, String> mapLoginPageCookies,
			final String producto,
			final Map<Integer,Boolean> mapDynEmpresas) 
					throws IOException, URISyntaxException, InterruptedException {

    	List<Document> listDocuments = Lists.newArrayList();
    	
		int idEmpresa = urlDto.getDidEmpresa();			
    	
    	Document document = getDocument(urlDto.getNomUrl(), idEmpresa, 
    			producto, mapLoginPageCookies, mapDynEmpresas);

    	List<String> liUrlsPorEmpresaPaginacion = urlsPaginacion(document, urlDto, idEmpresa);
   		 			
   		if(!liUrlsPorEmpresaPaginacion.isEmpty()) {
     			
	  		for (String url : liUrlsPorEmpresaPaginacion) {
	   			listDocuments.add(getDocument(url, idEmpresa, 
	   					producto, mapLoginPageCookies, mapDynEmpresas));
			}
	  		
   		} else {
   			listDocuments.add(document);
   		}
		      	
		return listDocuments;
	}
	
	protected List<String> urlsPaginacion(final Document document, 
			final UrlDTO urlDto, final int idEmpresa) 
					throws MalformedURLException {
		
		List<String> listUrlsResultado = Lists.newArrayList();
		
		listUrlsResultado.addAll(processDataEmpresasFactory
				.getEnterpriseData(idEmpresa).getListaUrls(document, urlDto));
		
		return listUrlsResultado;
	}
	
	protected Elements selectScrapPattern(final Document document,
			final String strScrapPattern, final String strScrapNotPattern) {

		Elements entradas;

        if(Objects.isNull(strScrapNotPattern)) {
        	entradas = document.select(strScrapPattern);
        } else {
        	entradas = document.select(strScrapPattern).not(strScrapNotPattern);
        }

        return entradas;
	}

	protected String eliminarTildes(final String cadena) {
			
		if(cadena.indexOf('\u00f1') != -1) {
			return cadena;
		}
		
		String resultado = cadena.replace("Ñ", UNICODE_ENIE);
		resultado = Normalizer.normalize(resultado.toLowerCase(), Normalizer.Form.NFD);
		resultado = resultado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", StringUtils.EMPTY);
		resultado = resultado.replace(UNICODE_ENIE,  "ñ");
		return Normalizer.normalize(resultado, Normalizer.Form.NFC);
	}

	protected Pattern createPatternProduct(final String[] arProducto) {

		List<String> tokens = Lists.newArrayList();
		
		List<String> listProducto = Arrays.asList(arProducto);  
		listProducto.forEach(elem -> tokens.add(elem.toUpperCase()));
		
		StringBuilder stringBuilder = new StringBuilder(10);
		
		stringBuilder.append("(");
		
		tokens.forEach(e -> stringBuilder.append(".*").append(e));
		
		stringBuilder.append(")");
		
		Collections.reverse(tokens);
		
		stringBuilder.append("|(");
		
		tokens.forEach(e -> stringBuilder.append(".*").append(e));

		stringBuilder.append(")");
		
		return Pattern.compile(stringBuilder.toString());
	}
	
	protected String filtroMarca(
			final int iIdEmpresa, 
			final String nomProducto, 
			final List<MarcasDTO> listTodasMarcas) {
		
		String strProducto;
		
		if(iFProcessDataHipercor.get_DID() == iIdEmpresa ||
				iFProcessDataDIA.get_DID() == iIdEmpresa ||
				iIdEmpresa == iFProcessDataECI.get_DID()) {
			strProducto = eliminarMarcaPrincipio(nomProducto);
		} else {
			strProducto = nomProducto;
		}
		
		final String strProductoEval = strProducto;
		listTodasMarcas.stream().filter(marcaDto -> marcaDto.getNomMarca().toLowerCase()
				.startsWith(strProductoEval.toLowerCase())).collect(Collectors.toList());
		
		return strProducto.toLowerCase().replaceAll(listTodasMarcas.get(0).getNomMarca().toLowerCase(), StringUtils.EMPTY).trim();
		
	}
	
	protected IFProcessPrice fillProcessPrice(final Element elem,
			final UrlDTO urlDto, 
			final String ordenacion) throws IOException {

		int idEmpresaActual = urlDto.getDidEmpresa();
		IFProcessPrice ifProcessPrice = new ProcessPriceModule(); 

		ifProcessPrice.setImagen(elementoPorCssSelector(elem, urlDto.getSelectores().getSelImage(), urlDto));
		ifProcessPrice.setNomProducto(elementoPorCssSelector(elem, urlDto.getSelectores().getSelProducto(), urlDto));
		ifProcessPrice.setDesProducto(elementoPorCssSelector(elem, urlDto.getSelectores().getSelProducto(), urlDto));
		ifProcessPrice.setPrecio(elementoPorCssSelector(elem, urlDto.getSelectores().getSelPrecio(), urlDto));
		ifProcessPrice.setPrecioKilo(elementoPorCssSelector(elem, urlDto.getSelectores().getSelPreKilo(), urlDto));
		ifProcessPrice.setNomUrl(elementoPorCssSelector(elem, urlDto.getSelectores().getSelLinkProd(), urlDto));
		ifProcessPrice.setDidEmpresa(urlDto.getDidEmpresa());
		ifProcessPrice.setNomEmpresa(urlDto.getNomEmpresa());

		if(ifProcessDataMercadona.get_DID() == idEmpresaActual) {
			ifProcessPrice.setNomUrlAllProducts(ifProcessDataMercadona.getUrlAll(ifProcessPrice));
			ifProcessPrice.setImagen(ifProcessPrice.getImagen().replace(",", "."));
		}else {
			ifProcessPrice.setNomUrlAllProducts(urlDto.getNomUrl());
		}
		
		ifProcessPrice.setOrdenacion(Integer.parseInt(ordenacion));		
		
		return ifProcessPrice;
	}

	protected String tratarProducto(final String producto) throws IOException {
		
		String productoTratado=anadirCaracteres(producto, Character.MIN_VALUE, 0, 0);
		productoTratado=anadirCaracteres(productoTratado, Character.MIN_VALUE, 0, 1);
		
		Matcher matcher = Pattern.compile("(\\%|\\$00)").matcher(productoTratado);
		
		if(matcher.find()) {
			return productoTratado;
		} else {
			return URLEncoder.encode(productoTratado, StandardCharsets.UTF_8.toString());
		}
	}
	
	private Document getDocument(final String strUrl, 
			final int didEmpresa, final String producto,
			final Map<String, String> mapLoginPageCookies,
			final Map<Integer,Boolean> mapDynEmpresas) 
					throws InterruptedException, URISyntaxException, IOException {
	
		Connection connection  = null;
		Response response = null;
		
		boolean isMercadona = didEmpresa == ifProcessDataMercadona.get_DID();	
		boolean bDynScrap = mapDynEmpresas.get(didEmpresa);
		URL url = new URL(strUrl);

		if(bDynScrap) {			
			return Jsoup.parse(procesDataDynamic.getDynHtmlContent(strUrl, didEmpresa), url.toURI().toString());
		} else if(isMercadona) {			
			connection = ifProcessDataMercadona.getConnection(strUrl, producto);	
			response = connection.execute();
		} else {			
			connection = Jsoup.connect(strUrl)
					.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
					.method(Connection.Method.GET)
					.referrer(url.getProtocol().concat(PROTOCOL_ACCESSOR).concat(url.getHost().concat("/")))
					.ignoreContentType(Boolean.TRUE)
					.header("Accept-Language", "es-ES,es;q=0.8")
					.header("Accept-Encoding", "gzip, deflate, sdch")
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
					.maxBodySize(0)
					.timeout(100000);
			response = connection.execute();
		}

		if(Objects.nonNull(mapLoginPageCookies)) {
			connection.cookies(mapLoginPageCookies);
		}
		
		if(isMercadona) {
       		return ifProcessDataMercadona.getDocument(strUrl, response.body());
       	} else {
			return response.parse();
		}
	}
	
	private String eliminarMarcaPrincipio(@NonNull final String nomProducto) {
		
		String[] nomProdSeparado = nomProducto.trim().split(StringUtils.SPACE);
				
		StringBuilder stringBuilder = new StringBuilder(10);
		
		Arrays.asList(nomProdSeparado).stream()
		.filter(palabra -> !palabra.toUpperCase().equals(palabra))
		.forEach(palabra -> { 
			stringBuilder.append(palabra).append(StringUtils.SPACE); 
		});
		
		return stringBuilder.toString();
	}	

	private String elementoPorCssSelector(final Element elem, 
			final String cssSelector,
			final UrlDTO urlDto) throws MalformedURLException {
				
		List<String> lista = Lists.newArrayList();
		String strResult;
		
		StringTokenizer st = new StringTokenizer(cssSelector,"|");  
		
		while (st.hasMoreTokens()) {  
			lista.add(st.nextToken());
		}
		
		int listaSize = lista.size();
		
		if(ifProcessDataMercadona.get_DID() == urlDto.getDidEmpresa()) {
			
			strResult = ifProcessDataMercadona.getResult(elem, cssSelector);
			
		} else if(ifProcessDataCondis.get_DID() == urlDto.getDidEmpresa() &&
				"script".equalsIgnoreCase(lista.get(0))) {	
			
			strResult = ifProcessDataCondis.tratarTagScript(elem, lista.get(0));
			
		} else if(iFProcessDataECI.get_DID() == urlDto.getDidEmpresa()) {
			
			strResult = elem.selectFirst(env
					.getProperty("flow.value.pagina.precio.eci.offer")).text();
		
		} else {
			
			strResult = extraerValorDelElemento(listaSize, elem, lista, cssSelector);
		
		}
		
		return validaResultadoElementValue(strResult, urlDto.getNomUrl());
	}
	
	private String validaResultadoElementValue(String strResult, 
			final String strUrl) throws MalformedURLException {
		
		int iend = -1;
		
		if(Objects.isNull(strResult)){
			return strResult;
		} else {
			iend = strResult.indexOf(LEFT_PARENTHESIS_0);
		}
				
		URL url = new URL(strUrl);
		String strUrlEmpresa = url.getProtocol().concat(PROTOCOL_ACCESSOR).concat(url.getHost());
		
		if(iend != -1) {
			strResult = strResult.substring(0, 
					strResult.indexOf(LEFT_PARENTHESIS_0)-1);
		}
		
		String caracteres = StringUtils.EMPTY;
		if(Objects.nonNull(strResult) && strResult.trim().startsWith("//")) {
			caracteres = "https:".concat(strResult);
		} else if(Objects.nonNull(strResult) && strResult.trim().startsWith("/")) {
			caracteres = strUrlEmpresa.concat(strResult); 
		} else if(Objects.nonNull(strResult)){
			caracteres = strResult;
		}
		 
		String resultado = caracteres.replaceAll("\\(", StringUtils.EMPTY);
		resultado = resultado.replaceAll("\\)", StringUtils.EMPTY);
		
		resultado = resultado.replace("€", " eur");
		resultado = resultado.replace("Kilo", "kg");
		resultado = resultado.replace(" / ", "/");
		resultado = resultado.replace(" \"", "\"");
		 
		return resultado;
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
	
	private String extraerValorDelElemento(int length,Element elem,
			List<String> lista,String cssSelector) {
			
		if(length == 1) {
			return elem.select(lista.get(0)).text();
		} else if(length == 2) {
			return elem.select(lista.get(0)).attr(lista.get(1));
		} else {
			return elem.select(cssSelector).text();
		}
	}
	
	protected IFProcessDataCondis getIFProcessDataCondis() {
		return ifProcessDataCondis;
	}
	
}
