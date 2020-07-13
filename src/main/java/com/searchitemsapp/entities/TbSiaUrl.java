package com.searchitemsapp.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * The persistent class for the tb_sia_urls database table.
 * 
 * @author Felix Marin Ramirez
 */
@Entity
@Component
@Table(name="tb_sia_urls", schema = "sia")
@NamedQuery(name="TbSiaUrl.findAll", query="SELECT t FROM TbSiaUrl t")
public class TbSiaUrl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "did")
	@org.hibernate.annotations.Type(type="didDef")
	private Integer did;

	@Column(name="bol_activo")
	@org.hibernate.annotations.Type(type="bolAct")
	private Boolean bolActivo;

	@Column(name="des_url")
	@org.hibernate.annotations.Type(type="strDef")
	private String desUrl;

	@Column(name="nom_url")
	@org.hibernate.annotations.Type(type="strDef")
	private String nomUrl;
	
	@Column(name="bol_status")
	@org.hibernate.annotations.Type(type="bolAct")
	private Boolean bolStatus;	
	
	@Column(name="bol_login")
	@org.hibernate.annotations.Type(type="bolAct")
	private Boolean bolLogin;	

	//bi-directional many-to-one association to TbSiaEmpresa
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_empresa", referencedColumnName="did", nullable = false)
	private TbSiaEmpresa tbSiaEmpresa;

	//bi-directional many-to-one association to TbSiaSelectoresCss
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="tbSiaUrl")
	private List<TbSiaSelectoresCss> tbSiaSelectoresCsses;	
	
	public TbSiaUrl() {
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

	public String getDesUrl() {
		return this.desUrl;
	}

	public void setDesUrl(String desUrl) {
		this.desUrl = desUrl;
	}

	public String getNomUrl() {
		return this.nomUrl;
	}

	public void setNomUrl(String nomUrl) {
		this.nomUrl = nomUrl;
	}

	public TbSiaEmpresa getTbSiaEmpresa() {
		return this.tbSiaEmpresa;
	}

	public void setTbSiaEmpresa(TbSiaEmpresa tbSiaEmpresa) {
		this.tbSiaEmpresa = tbSiaEmpresa;
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

	public List<TbSiaSelectoresCss> getTbSiaSelectoresCsses() {
		return tbSiaSelectoresCsses;
	}

	public void setTbSiaSelectoresCsses(List<TbSiaSelectoresCss> tbSiaSelectoresCsses) {
		this.tbSiaSelectoresCsses = tbSiaSelectoresCsses;
	}
}