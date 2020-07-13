package com.searchitemsapp.dto;

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
public class MarcasDTO implements IFdto {

	private Integer did;
	private String nomMarca;
	private int didCatEmpresas;
	private String nomCatEmpresas;	
	private int didPais;
	private String nomPais;
	
	public MarcasDTO() {
		super();
	}
	
	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public String getNomMarca() {
		return nomMarca;
	}

	public void setNomMarca(String nomMarca) {
		this.nomMarca = nomMarca;
	}
	
	public int getDidCatEmpresas() {
		return didCatEmpresas;
	}

	public void setDidCatEmpresas(int didCatEmpresas) {
		this.didCatEmpresas = didCatEmpresas;
	}

	public String getNomCatEmpresas() {
		return nomCatEmpresas;
	}

	public void setNomCatEmpresas(String nomCatEmpresas) {
		this.nomCatEmpresas = nomCatEmpresas;
	}

	public int getDidPais() {
		return didPais;
	}

	public void setDidPais(int didPais) {
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
		return "MarcasDTO [did=" + did + ", nomMarca=" + nomMarca + ", tbSiaCategoriasEmpresa="
				 + "]";
	}
}
