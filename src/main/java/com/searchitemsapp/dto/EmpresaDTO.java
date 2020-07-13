package com.searchitemsapp.dto;

import java.util.Map;

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
public class EmpresaDTO implements IFdto {

	private Integer did;
	private Boolean bolActivo;
	private String desEmpresa;
	private String nomEmpresa;
	private Boolean bolDynScrap;	
	
	private Integer didCatEmpresa;
	private String nomCatEmpresa;
	private Integer didPais;
	private String nomPais;
	private Map<String, String> selectores;
	private Map<Integer, String> urls;

	public EmpresaDTO() {
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

	public String getDesEmpresa() {
		return desEmpresa;
	}

	public void setDesEmpresa(String desEmpresa) {
		this.desEmpresa = desEmpresa;
	}

	public String getNomEmpresa() {
		return nomEmpresa;
	}

	public void setNomEmpresa(String nomEmpresa) {
		this.nomEmpresa = nomEmpresa;
	}

	public Boolean getBolDynScrap() {
		return bolDynScrap;
	}

	public void setBolDynScrap(Boolean bolDynScrap) {
		this.bolDynScrap = bolDynScrap;
	}
	
	public Integer getDidCatEmpresa() {
		return didCatEmpresa;
	}

	public void setDidCatEmpresa(Integer didCatEmpresa) {
		this.didCatEmpresa = didCatEmpresa;
	}

	public String getNomCatEmpresa() {
		return nomCatEmpresa;
	}

	public void setNomCatEmpresa(String nomCatEmpresa) {
		this.nomCatEmpresa = nomCatEmpresa;
	}
	
	public Map<String, String> getSelectores() {
		return selectores;
	}

	public void setSelectores(Map<String, String> selectores) {
		this.selectores = selectores;
	}
	
	public Map<Integer, String> getUrls() {
		return urls;
	}

	public void setUrls(Map<Integer, String> urls) {
		this.urls = urls;
	}
	
	public Integer getDidPais() {
		return didPais;
	}

	public void setDidPais(Integer didPais) {
		this.didPais = didPais;
	}

	public String getNomPais() {
		return nomPais;
	}

	public void setNomPais(String nomPais) {
		this.nomPais = nomPais;
	}

	@Override
	public String toString() {
		return "EmpresaDTO [did=" + did + ", bolActivo=" + bolActivo + ", desEmpresa=" + desEmpresa + ", nomEmpresa="
				+ nomEmpresa + ", bolDynScrap=" + bolDynScrap + ", didCatEmpresa=" + didCatEmpresa + ", nomCatEmpresa="
				+ nomCatEmpresa + ", didPais=" + didPais + ", nomPais=" + nomPais + ", selectores=" + selectores
				+ ", urls=" + urls + "]";
	}
}
