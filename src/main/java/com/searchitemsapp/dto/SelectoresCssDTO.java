package com.searchitemsapp.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

/**
 * Objeto de Transferencia de Datos (DTO) 
 * es un objeto que transporta datos entre procesos.
 * No tiene más comportamiento que almacenar y entregar 
 * sus propios datos.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public class SelectoresCssDTO  implements IFdto {
	
	private Integer did;
	private Boolean bolActivo;
	private LocalDate fecModificacion;
	private String scrapNoPattern;
	private String scrapPattern;
	private String selImage;
	private String selLinkProd;
	private String selPreKilo;
	private String selPrecio;
	private String selProducto;
	private String selPaginacion;	
	private Integer didEmpresa;
	private String nomEmpresa;	
	private Integer didUrl;
	private String nomUrl;

	public SelectoresCssDTO() {
		super();
	}

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public Boolean getBolActivo() {
		return bolActivo;
	}

	public void setBolActivo(Boolean bolActivo) {
		this.bolActivo = bolActivo;
	}

	public LocalDate getFecModificacion() {
		return fecModificacion;
	}

	public void setFecModificacion(LocalDate fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public String getScrapNoPattern() {
		return scrapNoPattern;
	}

	public void setScrapNoPattern(String scrapNoPattern) {
		this.scrapNoPattern = scrapNoPattern;
	}

	public String getScrapPattern() {
		return scrapPattern;
	}

	public void setScrapPattern(String scrapPattern) {
		this.scrapPattern = scrapPattern;
	}

	public String getSelImage() {
		return selImage;
	}

	public void setSelImage(String selImage) {
		this.selImage = selImage;
	}

	public String getSelLinkProd() {
		return selLinkProd;
	}

	public void setSelLinkProd(String selLinkProd) {
		this.selLinkProd = selLinkProd;
	}

	public String getSelPreKilo() {
		return selPreKilo;
	}

	public void setSelPreKilo(String selPreKilo) {
		this.selPreKilo = selPreKilo;
	}

	public String getSelPrecio() {
		return selPrecio;
	}

	public void setSelPrecio(String selPrecio) {
		this.selPrecio = selPrecio;
	}

	public String getSelProducto() {
		return selProducto;
	}

	public void setSelProducto(String selProducto) {
		this.selProducto = selProducto;
	}

	public String getSelPaginacion() {
		return selPaginacion;
	}

	public void setSelPaginacion(String selPaginacion) {
		this.selPaginacion = selPaginacion;
	}

	public Integer getDidEmpresa() {
		return didEmpresa;
	}

	public void setDidEmpresa(Integer didEmpresa) {
		this.didEmpresa = didEmpresa;
	}

	public String getNomEmpresa() {
		return nomEmpresa;
	}

	public void setNomEmpresa(String nomEmpresa) {
		this.nomEmpresa = nomEmpresa;
	}

	public Integer getDidUrl() {
		return didUrl;
	}

	public void setDidUrl(Integer didUrl) {
		this.didUrl = didUrl;
	}

	public String getNomUrl() {
		return nomUrl;
	}

	public void setNomUrl(String nomUrl) {
		this.nomUrl = nomUrl;
	}

	@Override
	public String toString() {
		return "SelectoresCssDTO [did=" + did + ", bolActivo=" + bolActivo + ", fecModificacion=" + fecModificacion
				+ ", scrapNoPattern=" + scrapNoPattern + ", scrapPattern=" + scrapPattern + ", selImage=" + selImage
				+ ", selLinkProd=" + selLinkProd + ", selPreKilo=" + selPreKilo + ", selPrecio=" + selPrecio
				+ ", selProducto=" + selProducto + ", selPaginacion=" + selPaginacion + ", didEmpresa=" + didEmpresa
				+ ", nomEmpresa=" + nomEmpresa + ", didUrl=" + didUrl + ", nomUrl=" + nomUrl + "]";
	}
}
