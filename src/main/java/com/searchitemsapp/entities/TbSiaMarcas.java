package com.searchitemsapp.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name="tb_sia_marcas", schema = "sia")
@NamedQuery(name="TbSiaMarcas.findAll", query="SELECT t FROM TbSiaMarcas t")
public class TbSiaMarcas implements Serializable {
	private static final long serialVersionUID = 1L;

	public TbSiaMarcas() {
		super();
	}	
	
	@Id
	@Column(name = "did")
	private Integer did;

	@Column(name="nom_marca")
	private String nomMarca;

	//bi-directional many-to-one association to TbSiaCategoriasEmpresa
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="did_categoria", referencedColumnName="did", nullable = false)
	private TbSiaCategoriasEmpresa tbSiaCategoriasEmpresa;

	//bi-directional many-to-one association to TbSiaPais
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="did_pais", referencedColumnName="did", nullable = false)
	private TbSiaPais tbSiaPais;

	
	public Integer getDid() {
		return this.did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public String getNomMarca() {
		return this.nomMarca;
	}

	public void setNomMarca(String nomMarca) {
		this.nomMarca = nomMarca;
	}

	public TbSiaCategoriasEmpresa getTbSiaCategoriasEmpresa() {
		return this.tbSiaCategoriasEmpresa;
	}

	public void setTbSiaCategoriasEmpresa(TbSiaCategoriasEmpresa tbSiaCategoriasEmpresa) {
		this.tbSiaCategoriasEmpresa = tbSiaCategoriasEmpresa;
	}

	public TbSiaPais getTbSiaPais() {
		return this.tbSiaPais;
	}

	public void setTbSiaPais(TbSiaPais tbSiaPais) {
		this.tbSiaPais = tbSiaPais;
	}

}