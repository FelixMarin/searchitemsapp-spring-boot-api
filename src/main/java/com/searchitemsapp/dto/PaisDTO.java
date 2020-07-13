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
public class PaisDTO implements IFdto {
	
	private Integer did;
	private Boolean bolActivo;
	private String desPais;
	private String nomPais;
	
	private Map<Integer, String> empresas;
	private Map<Integer, String> marcas;
	private Map<Integer, String> productos;
	
	public PaisDTO() {	
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

	public String getDesPais() {
		return desPais;
	}

	public void setDesPais(String desPais) {
		this.desPais = desPais;
	}

	public String getNomPais() {
		return nomPais;
	}

	public void setNomPais(String nomPais) {
		this.nomPais = nomPais;
	}
	
	public Map<Integer, String> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(Map<Integer, String> empresas) {
		this.empresas = empresas;
	}

	public Map<Integer, String> getMarcas() {
		return marcas;
	}

	public void setMarcas(Map<Integer, String> marcas) {
		this.marcas = marcas;
	}

	public Map<Integer, String> getProductos() {
		return productos;
	}

	public void setProductos(Map<Integer, String> productos) {
		this.productos = productos;
	}

	@Override
	public String toString() {
		return "PaisDTO [did=" + did + ", bolActivo=" + bolActivo + ", desPais=" + desPais + ", nomPais=" + nomPais
				 + "]";
	}
}
