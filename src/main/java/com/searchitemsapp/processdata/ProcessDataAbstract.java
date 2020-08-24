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
import org.codehaus.jettison.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.dto.UrlDTO;
import com.searchitemsapp.processdata.empresas.ProcessDataCondis;
import com.searchitemsapp.processdata.empresas.ProcessDataDia;
import com.searchitemsapp.processdata.empresas.ProcessDataEci;
import com.searchitemsapp.processdata.empresas.ProcessDataEmpresasFactory;
import com.searchitemsapp.processdata.empresas.ProcessDataEroski;
import com.searchitemsapp.processdata.empresas.ProcessDataHipercor;
import com.searchitemsapp.processdata.empresas.ProcessDataMercadona;
import com.searchitemsapp.processdata.empresas.ProcessDataSimply;
import com.searchitemsapp.processdata.empresas.lambdas.CondisTratarScript;
import com.searchitemsapp.processdata.empresas.lambdas.MercadonaGetConnection;
import com.searchitemsapp.processdata.empresas.lambdas.MercadonaGetDocument;
import com.searchitemsapp.processdata.empresas.lambdas.MercadonaGetUrlAll;

import lombok.NonNull;

@Component
public abstract class ProcessDataAbstract {
	
	private static final String ZERO_STRING = "0";
	private static final String COMMA_STRING = ",";
	private static final char LEFT_SLASH_CHAR = '\'';
	private static final String SPECIALS_CHARS_STRING = "\r\n|\r|\n";
	private static final String PROTOCOL_ACCESSOR ="://";
	private static final String UNICODE_ENIE = "u00f1";	
	private static final String LEFT_PARENTHESIS_0 = " (";
	
	@Autowired 
	private ProcessDataDynamic procesDataDynamic;
	
	@Autowired
	private ProcessDataEmpresasFactory processDataEmpresasFactory;
	
	@Autowired
	private ProcessDataMercadona ifProcessDataMercadona;
				
	@Autowired
	private ProcessDataCondis ifProcessDataCondis;
	
	@Autowired
	private ProcessDataEci iFProcessDataECI;
	
	@Autowired
	private ProcessDataHipercor iFProcessDataHipercor;
	
	@Autowired
	private ProcessDataDia iFProcessDataDIA;
	
	@Autowired
	private ProcessDataEroski ifProcessDataEroski;
	
	@Autowired
	private ProcessDataSimply ifProcessDataSimply;
	
	@Autowired
	private Environment env;
	
	protected List<Document> getHtmlDocument(final UrlDTO urlDto, 
			final Map<String, String> mapLoginPageCookies,
			final String producto,
			final Map<Integer,Boolean> mapDynEmpresas) 
					throws IOException, URISyntaxException, 
					InterruptedException, JSONException {

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
	
	protected ProcessPrice fillProcessPrice(final Element elem,
			final UrlDTO urlDto, 
			final String ordenacion) throws IOException {

		int idEmpresaActual = urlDto.getDidEmpresa();
		ProcessPrice ifProcessPrice = new ProcessPriceModuleImpl(); 

		ifProcessPrice.setImagen(elementoPorCssSelector(elem, urlDto.getSelectores().getSelImage(), urlDto));
		ifProcessPrice.setNomProducto(elementoPorCssSelector(elem, urlDto.getSelectores().getSelProducto(), urlDto));
		ifProcessPrice.setDesProducto(elementoPorCssSelector(elem, urlDto.getSelectores().getSelProducto(), urlDto));
		ifProcessPrice.setPrecio(elementoPorCssSelector(elem, urlDto.getSelectores().getSelPrecio(), urlDto));
		ifProcessPrice.setPrecioKilo(elementoPorCssSelector(elem, urlDto.getSelectores().getSelPreKilo(), urlDto));
		ifProcessPrice.setNomUrl(elementoPorCssSelector(elem, urlDto.getSelectores().getSelLinkProd(), urlDto));
		ifProcessPrice.setDidEmpresa(urlDto.getDidEmpresa());
		ifProcessPrice.setNomEmpresa(urlDto.getNomEmpresa());

		if(ifProcessDataMercadona.get_DID() == idEmpresaActual) {
			
			MercadonaGetUrlAll urlAll = (processPrice) -> {
				String productoAux = StringUtils.EMPTY;
				
				if(!StringUtils.isAllEmpty(processPrice.getNomProducto())) {
					productoAux= processPrice.getNomProducto()
						.replace(StringUtils.SPACE, "%20");
				}
				
				return env.getProperty("flow.value.url.all").concat(productoAux);
			};
			
			ifProcessPrice.setNomUrlAllProducts(urlAll.getUrlAll(ifProcessPrice));
			ifProcessPrice.setImagen(ifProcessPrice.getImagen().replace(COMMA_STRING, "."));
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
					throws InterruptedException, URISyntaxException, IOException, JSONException {
	
		Connection connection  = null;
		Response response = null;
		
		boolean isMercadona = didEmpresa == ifProcessDataMercadona.get_DID();	
		boolean bDynScrap = mapDynEmpresas.get(didEmpresa);
		URL url = new URL(strUrl);

		if(bDynScrap) {			
			return Jsoup.parse(procesDataDynamic.getDynHtmlContent(strUrl, didEmpresa), url.toURI().toString());
		} else if(isMercadona) {
			
			MercadonaGetConnection mercadonaConnection = (stUrl,prd) -> {
				
				return Jsoup.connect(stUrl)
						.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
						.method(Connection.Method.POST)
						.referrer("https://tienda.mercadona.es/")
						.ignoreContentType(Boolean.TRUE)
						.header("Accept-Language", "es-ES,es;q=0.8")
						.header("Accept-Encoding", "gzip, deflate, sdch")
						.header("Accept", "application/json")
						.maxBodySize(0)
						.timeout(100000)
						.requestBody("{\"params\":\"query="
								.concat(prd)
								.concat("&clickAnalytics=true\"}"));
				
			};
			
			connection = mercadonaConnection.getConnection(strUrl, producto);	
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
			
			MercadonaGetDocument document = (stUrl, body) -> {
				
				JSONObject json = new JSONObject(body);
				
				String xml = XML.toString(json);
				
				xml = xml.replace(".", COMMA_STRING);
				
				if(StringUtils.isAllEmpty(xml)) {
					return new Document(StringUtils.EMPTY);
				} else {
					xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"
							.concat("<root>").concat(xml).concat("</root>");
					xml = xml.replace("&lt;em&gt;", StringUtils.EMPTY);
					xml = xml.replace("&lt;/em&gt;", StringUtils.EMPTY);
				}
				
				Document doc = Jsoup.parse(xml, StringUtils.EMPTY, Parser.xmlParser());
				doc.setBaseUri(stUrl);
				
				return doc;
			};
			
       		return document.getDocument(strUrl, response.body());
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
			
			strResult = elem.selectFirst(cssSelector).text();
			
		} else if(ifProcessDataCondis.get_DID() == urlDto.getDidEmpresa() &&
				"script".equalsIgnoreCase(lista.get(0))) {	
			
			CondisTratarScript tratarScript = (elemento, selectorCss) -> {

				String resultado = StringUtils.EMPTY;
				Matcher matcher;
				
				if(Objects.isNull(elemento) || StringUtils.isAllEmpty(cssSelector)) {
					return resultado;
				}
				resultado = elem.select(selectorCss).html().replace(".", COMMA_STRING);
				
				if(resultado.split(SPECIALS_CHARS_STRING).length > 1) {
					resultado = resultado.split(SPECIALS_CHARS_STRING)[1].trim();
					
					matcher = Pattern.compile("\\d*[,][0-9]*").matcher(resultado);
					
					if(matcher.find()) {
						resultado = matcher.group(0);
					}
				
				} else {
					resultado = resultado.substring(resultado.indexOf(LEFT_SLASH_CHAR)+1, resultado.length());
					resultado = resultado.substring(0, resultado.indexOf(LEFT_SLASH_CHAR));
					
					if(resultado.contains(COMMA_STRING) &&
							resultado.substring(resultado.indexOf(COMMA_STRING), 
									resultado.length()).length()  == 2) {
						resultado += ZERO_STRING;
					}else {
						resultado = resultado.concat(",00");
					}
				}
				
				if(resultado.startsWith(COMMA_STRING)) {
					resultado = ZERO_STRING.concat(resultado);
				}
				
				if(resultado.endsWith(COMMA_STRING)) {
					resultado = resultado.concat("00");
				}
				
				return resultado;
			};
			
			strResult = tratarScript.tratarTagScript(elem, lista.get(0));
			
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
	
	protected ProcessDataCondis getIFProcessDataCondis() {
		return ifProcessDataCondis;
	}
	
	protected ProcessDataEroski getIFProcessDataEroski() {
		return ifProcessDataEroski;
	}
	
	protected ProcessDataSimply getIFProcessDataSimply() {
		return ifProcessDataSimply;
	}
	
}
