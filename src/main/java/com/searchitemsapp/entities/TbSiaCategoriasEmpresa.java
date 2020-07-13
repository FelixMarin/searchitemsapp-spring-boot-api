package com.searchitemsapp.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
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
 * The persistent class for the tb_sia_categorias_empresas database table.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Entity
@Component
@Table(name="tb_sia_categorias_empresas", schema = "sia")
@NamedQuery(name="TbSiaCategoriasEmpresa.findAll", query="SELECT t FROM TbSiaCategoriasEmpresa t")
public class TbSiaCategoriasEmpresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "did")
	@org.hibernate.annotations.Type(type="didDef")
	private Integer did;

	@Column(name="bol_activo")
	@org.hibernate.annotations.Type(type="bolAct")
	private Boolean bolActivo;

	@Column(name="des_cat_empresa")
	@org.hibernate.annotations.Type(type="strDef")
	private String desCatEmpresa;

	@Column(name="nom_cat_empresa")
	@org.hibernate.annotations.Type(type="strDef")
	private String nomCatEmpresa;

	//bi-directional many-to-one association to TbSiaEmpresa
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy="tbSiaCategoriasEmpresa")
	private List<TbSiaEmpresa> tbSiaEmpresas;

	//bi-directional many-to-one association to TbSiaMarca
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy="tbSiaCategoriasEmpresa")
	private List<TbSiaMarcas> tbSiaMarcas;

	//bi-directional many-to-one association to TbSiaNomProducto
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy="tbSiaCategoriasEmpresa")
	private List<TbSiaNomProducto> tbSiaNomProductos;

	public TbSiaCategoriasEmpresa() {
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

	public String getDesCatEmpresa() {
		return this.desCatEmpresa;
	}

	public void setDesCatEmpresa(String desCatEmpresa) {
		this.desCatEmpresa = desCatEmpresa;
	}

	public String getNomCatEmpresa() {
		return this.nomCatEmpresa;
	}

	public void setNomCatEmpresa(String nomCatEmpresa) {
		this.nomCatEmpresa = nomCatEmpresa;
	}

	public List<TbSiaEmpresa> getTbSiaEmpresas() {
		return this.tbSiaEmpresas;
	}

	public void setTbSiaEmpresas(List<TbSiaEmpresa> tbSiaEmpresas) {
		this.tbSiaEmpresas = tbSiaEmpresas;
	}

	public TbSiaEmpresa addTbSiaEmpresa(TbSiaEmpresa tbSiaEmpresa) {
		this.tbSiaEmpresas.add(tbSiaEmpresa);
		tbSiaEmpresa.setTbSiaCategoriasEmpresa(this);

		return tbSiaEmpresa;
	}

	public TbSiaEmpresa removeTbSiaEmpresa(TbSiaEmpresa tbSiaEmpresa) {
		this.tbSiaEmpresas.remove(tbSiaEmpresa);
		tbSiaEmpresa.setTbSiaCategoriasEmpresa(null);

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
		tbSiaNomProducto.setTbSiaCategoriasEmpresa(this);

		return tbSiaNomProducto;
	}

	public TbSiaNomProducto removeTbSiaNomProducto(TbSiaNomProducto tbSiaNomProducto) {
		getTbSiaNomProductos().remove(tbSiaNomProducto);
		tbSiaNomProducto.setTbSiaCategoriasEmpresa(null);

		return tbSiaNomProducto;
	}

}