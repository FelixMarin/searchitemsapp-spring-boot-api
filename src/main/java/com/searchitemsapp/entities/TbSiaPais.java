package com.searchitemsapp.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.TypeDef;
import org.springframework.stereotype.Component;

/**
 * Definición de tipos customizados.
 */
@TypeDef(
		   name = "didDef",
		   defaultForType = Integer.class,
		   typeClass = Integer.class
		)

@TypeDef(
		   name = "bolAct",
		   defaultForType = Boolean.class,
		   typeClass = Boolean.class
		)

@TypeDef(
		   name = "strDef",
		   defaultForType = String.class,
		   typeClass = String.class
		)

/**
 * The persistent class for the tb_sia_paises database table.
 * 
 *  @author Felix Marin Ramirez
 */
@Entity
@Component
@Table(name="tb_sia_paises", schema = "sia")
@NamedQuery(name="TbSiaPais.findAll", query="SELECT t FROM TbSiaPais t")
public class TbSiaPais implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "did")
	@org.hibernate.annotations.Type(type="didDef")
	private Integer did;


	@Column(name="bol_activo")
	@org.hibernate.annotations.Type(type="bolAct")
	private Boolean bolActivo;

	@Column(name="des_pais")
	@org.hibernate.annotations.Type(type="strDef")
	private String desPais;

	@Column(name="nom_pais")
	@org.hibernate.annotations.Type(type="strDef")
	private String nomPais;

	//bi-directional many-to-one association to TbSiaEmpresa
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="tbSiaPais")
	private List<TbSiaEmpresa> tbSiaEmpresas;

	//bi-directional many-to-one association to TbSiaMarca
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="tbSiaPais")
	private List<TbSiaMarcas> tbSiaMarcas;

	//bi-directional many-to-one association to TbSiaNomProducto
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="tbSiaPais")
	private List<TbSiaNomProducto> tbSiaNomProductos;

	public TbSiaPais() {
		super();
	}

	public Integer getDid() {
		return this.did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public Boolean getBolActivo() {
		return this.bolActivo;
	}

	public void setBolActivo(Boolean bolActivo) {
		this.bolActivo = bolActivo;
	}

	public String getDesPais() {
		return this.desPais;
	}

	public void setDesPais(String desPais) {
		this.desPais = desPais;
	}

	public String getNomPais() {
		return this.nomPais;
	}

	public void setNomPais(String nomPais) {
		this.nomPais = nomPais;
	}

	public List<TbSiaEmpresa> getTbSiaEmpresas() {
		return this.tbSiaEmpresas;
	}

	public void setTbSiaEmpresas(List<TbSiaEmpresa> tbSiaEmpresas) {
		this.tbSiaEmpresas = tbSiaEmpresas;
	}

	public TbSiaEmpresa addTbSiaEmpresa(TbSiaEmpresa tbSiaEmpresa) {
		getTbSiaEmpresas().add(tbSiaEmpresa);
		tbSiaEmpresa.setTbSiaPais(this);

		return tbSiaEmpresa;
	}

	public TbSiaEmpresa removeTbSiaEmpresa(TbSiaEmpresa tbSiaEmpresa) {
		getTbSiaEmpresas().remove(tbSiaEmpresa);
		tbSiaEmpresa.setTbSiaPais(null);

		return tbSiaEmpresa;
	}

	public List<TbSiaMarcas> getTbSiaMarcas() {
		return this.tbSiaMarcas;
	}

	public void setTbSiaMarcas(List<TbSiaMarcas> tbSiaMarcas) {
		this.tbSiaMarcas = tbSiaMarcas;
	}

	public TbSiaMarcas addTbSiaMarca(TbSiaMarcas tbSiaMarca) {
		getTbSiaMarcas().add(tbSiaMarca);
		return tbSiaMarca;
	}

	public TbSiaMarcas removeTbSiaMarca(TbSiaMarcas tbSiaMarca) {
		getTbSiaMarcas().remove(tbSiaMarca);
		return tbSiaMarca;
	}

	public List<TbSiaNomProducto> getTbSiaNomProductos() {
		return this.tbSiaNomProductos;
	}

	public void setTbSiaNomProductos(List<TbSiaNomProducto> tbSiaNomProductos) {
		this.tbSiaNomProductos = tbSiaNomProductos;
	}

	public TbSiaNomProducto addTbSiaNomProducto(TbSiaNomProducto tbSiaNomProducto) {
		getTbSiaNomProductos().add(tbSiaNomProducto);
		tbSiaNomProducto.setTbSiaPais(this);

		return tbSiaNomProducto;
	}

	public TbSiaNomProducto removeTbSiaNomProducto(TbSiaNomProducto tbSiaNomProducto) {
		getTbSiaNomProductos().remove(tbSiaNomProducto);
		tbSiaNomProducto.setTbSiaPais(null);

		return tbSiaNomProducto;
	}

}