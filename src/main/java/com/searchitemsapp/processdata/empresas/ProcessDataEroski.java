package com.searchitemsapp.processdata.empresas;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDTO;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ProcessDataEroski implements IFProcessDataEroski {
	
	private static final String STRING_ENIE_MIN = "ñ";
	private static final String ENIE_EROSKI = "$00f1";
	private static final String[] ARRAY_TILDES_EROSKI = {"$00e1","$00e9","$00ed","$00f3","$00fa"};
	private static final String[] ARRAY_TILDES_NORMALES_MIN = {"á","é","í","ó","ú"};
	
	@Autowired
	private Environment env;

	@Override
	public List<String> getListaUrls(final Document document, final UrlDTO urlDto) 
			throws MalformedURLException {
		
		/**
		 * Se obtiene la URL base. Esta es la URL principal 
		 * del conjunto de páginas obtenidas como resultado
		 * de la búsqueda del producto. A partir de esta URL 
		 * se generan las de paginación.
		 */
		String urlBase = urlDto.getNomUrl();
		
		/**
		 * Se obtiene del fichero de propiedades el número máximo de
		 * páginas que se van a pedir al sitio web.
		 */	
		int numresultados = NumberUtils.toInt(env.getProperty("flow.value.paginacion.url.eroski"));
		
		/**
		 * Se añade la URL base a la lista en formato string.
		 */
		List<String> listaUrls = Lists.newArrayList();
		listaUrls.add(urlBase);
		
		/**
		 * Se compone la lista de URLs que se van a solicitar 
		 * al sitio web.
		 */
		for (int i = 1; i <= 20; i++) {
			listaUrls.add(urlBase.replace("=0&", "=".concat(String.valueOf(i).concat("&"))));
		}
		
		/**
		 * Se eliminan las URLs que superen el número maximo
		 * de resultados permitidos indicados en la variable
		 * 'numeroresultados'.
		 */
		if(numresultados > 0 && numresultados <= listaUrls.size()) {
			listaUrls = listaUrls.subList(0, numresultados);
		}		
		
		return listaUrls;
	}

	public String reemplazarCaracteres(final String producto) {
		
		String productoTratado = producto
				.replace(STRING_ENIE_MIN, ENIE_EROSKI);
		
		for (int i = 0; i < ARRAY_TILDES_EROSKI.length; i++) {
			productoTratado = productoTratado
					.replace(ARRAY_TILDES_NORMALES_MIN[i], ARRAY_TILDES_EROSKI[i]);
		}
		
		return productoTratado;
	}

	@Override
	public int get_DID() {
		return NumberUtils.toInt(env.getProperty("flow.value.did.empresa.eroski"));
	}
}
