package com.searchitemsapp.processdata.empresas;

import java.net.MalformedURLException;
import java.util.List;

import org.jsoup.nodes.Document;

import com.searchitemsapp.dto.UrlDTO;

/**
 * Representa de forma abstracta el objeto que queremos crear, 
 * mediante esta interface se definen la estructura que tendrán
 * los objetos 'Scraping'. 
 * 
 * @author Felix Marin Ramirez
 *
 */
public interface IFProcessDataEmpresas {

	public abstract List<String> getListaUrls(final Document document, final UrlDTO urlDto) throws MalformedURLException;
}
