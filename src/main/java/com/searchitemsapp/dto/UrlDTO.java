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
public class UrlDTO implements IFdto {

	private Integer did;
	private Boolean bolActivo;
	private String desUrl;
	private String nomUrl;
	private Integer didEmpresa;
	private String nomEmpresa;
	private Boolean bolStatus;	
	private Boolean bolLogin;	
	
	private Map<String, String> selectores;
	
	public UrlDTO() {
		super();
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

	public String getDesUrl() {
		return desUrl;
	}

	public void setDesUrl(String desUrl) {
		this.desUrl = desUrl;
	}

	public String getNomUrl() {
		return nomUrl;
	}

	public void setNomUrl(String nomUrl) {
		this.nomUrl = nomUrl;
	}

	public Boolean getBolStatus() {
		return this.bolStatus;
	}

	public void setBolStatus(Boolean bolStatus) {
		this.bolStatus = bolStatus;
	}
	
	public Boolean getBolLogin() {
		return bolLogin;
	}

	public void setBolLogin(Boolean bolLogin) {
		this.bolLogin = bolLogin;
	}

	public Map<String, String> getSelectores() {
		return selectores;
	}

	public void setSelectores(Map<String, String> selectores) {
		this.selectores = selectores;
	}

	@Override
	public String toString() {
		return "UrlDTO [did=" + did + ", bolActivo=" + bolActivo + ", desUrl=" + desUrl + ", nomUrl=" + nomUrl
				+ ", bolStatus=" + bolStatus + ", bolLogin=" + bolLogin
				 + "]";
	}
}
