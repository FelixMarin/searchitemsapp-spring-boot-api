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
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Entity
@Component
@NoArgsConstructor
@Table(name="tb_sia_categorias_empresas", schema = "sia")
@NamedQuery(name="TbSiaCategoriasEmpresa.findAll", query="SELECT t FROM TbSiaCategoriasEmpresa t")
public class TbSiaCategoriasEmpresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "did")
	private Long did;

	@Column(name="bol_activo")
	private Boolean bolActivo;

	@Column(name="des_cat_empresa")
	private String desCatEmpresa;

	@Column(name="nom_cat_empresa")
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

	public Long getDid() {
		return this.did;
	}

	public void setDid(Long did) {
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

	public void setTbSiaNomProductos(List<TbSiaNomProducto> tbSiaNomProductos) {
		this.tbSiaNomProductos = tbSiaNomProductos;
	}
	
	public List<TbSiaNomProducto> getTbSiaNomProductos() {
		return this.tbSiaNomProductos;
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

	public TbSiaEmpresa removeTbSiaEmpresa(TbSiaEmpresa tbSiaEmpresa) {
		this.tbSiaEmpresas.remove(tbSiaEmpresa);
		tbSiaEmpresa.setTbSiaCategoriasEmpresa(null);

		return tbSiaEmpresa;
	}
	
	public TbSiaMarcas removeTbSiaMarca(TbSiaMarcas tbSiaMarca) {
		getTbSiaMarcas().remove(tbSiaMarca);
		return tbSiaMarca;
	}
}