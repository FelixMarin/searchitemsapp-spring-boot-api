package com.searchitemsapp.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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

@TypeDef(
		   name = "dateDef",
		   defaultForType = Timestamp.class,
		   typeClass = Timestamp.class
		)

/**
 * The persistent class for the tb_sia_selectores_css database table.
 * 
 *  @author Felix Marin Ramirez
 */
@Entity
@Component
@Table(name="tb_sia_selectores_css", schema = "sia")
@NamedQuery(name="TbSiaSelectoresCss.findAll", query="SELECT t FROM TbSiaSelectoresCss t")
public class TbSiaSelectoresCss implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "did")
	@org.hibernate.annotations.Type(type="didDef")
	private Integer did;

	@Column(name="bol_activo")
	@org.hibernate.annotations.Type(type="bolAct")
	private Boolean bolActivo;

	@Column(name="fec_modificacion", columnDefinition = "DATE")
	private LocalDate fecModificacion;

	@Column(name="scrap_no_pattern")
	@org.hibernate.annotations.Type(type="strDef")
	private String scrapNoPattern;

	@Column(name="scrap_pattern")
	@org.hibernate.annotations.Type(type="strDef")
	private String scrapPattern;

	@Column(name="sel_image")
	@org.hibernate.annotations.Type(type="strDef")
	private String selImage;

	@Column(name="sel_link_prod")
	@org.hibernate.annotations.Type(type="strDef")
	private String selLinkProd;

	@Column(name="sel_pre_kilo")
	@org.hibernate.annotations.Type(type="strDef")
	private String selPreKilo;

	@Column(name="sel_precio")
	@org.hibernate.annotations.Type(type="strDef")
	private String selPrecio;

	@Column(name="sel_producto")
	@org.hibernate.annotations.Type(type="strDef")
	private String selProducto;
	
	@Column(name="sel_paginacion")
	@org.hibernate.annotations.Type(type="strDef")
	private String selPaginacion;	

	//bi-directional many-to-one association to TbSiaEmpresa
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="did_empresa", referencedColumnName="did", nullable = false)
	private TbSiaEmpresa tbSiaEmpresa;

	//bi-directional many-to-one association to TbSiaUrl
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="did_url", referencedColumnName="did", nullable = false)
	private TbSiaUrl tbSiaUrl;

	/*
	 * Constructor
	 */
	public TbSiaSelectoresCss() {
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

	public LocalDate getFecModificacion() {
		return this.fecModificacion;
	}

	public void setFecModificacion(LocalDate fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public String getScrapNoPattern() {
		return this.scrapNoPattern;
	}

	public void setScrapNoPattern(String scrapNoPattern) {
		this.scrapNoPattern = scrapNoPattern;
	}

	public String getScrapPattern() {
		return this.scrapPattern;
	}

	public void setScrapPattern(String scrapPattern) {
		this.scrapPattern = scrapPattern;
	}

	public String getSelImage() {
		return this.selImage;
	}

	public void setSelImage(String selImage) {
		this.selImage = selImage;
	}

	public String getSelLinkProd() {
		return this.selLinkProd;
	}

	public void setSelLinkProd(String selLinkProd) {
		this.selLinkProd = selLinkProd;
	}

	public String getSelPreKilo() {
		return this.selPreKilo;
	}

	public void setSelPreKilo(String selPreKilo) {
		this.selPreKilo = selPreKilo;
	}

	public String getSelPrecio() {
		return this.selPrecio;
	}

	public void setSelPrecio(String selPrecio) {
		this.selPrecio = selPrecio;
	}

	public String getSelProducto() {
		return this.selProducto;
	}

	public void setSelProducto(String selProducto) {
		this.selProducto = selProducto;
	}

	public TbSiaEmpresa getTbSiaEmpresa() {
		return this.tbSiaEmpresa;
	}

	public void setTbSiaEmpresa(TbSiaEmpresa tbSiaEmpresa) {
		this.tbSiaEmpresa = tbSiaEmpresa;
	}

	public TbSiaUrl getTbSiaUrl() {
		return this.tbSiaUrl;
	}

	public void setTbSiaUrl(TbSiaUrl tbSiaUrl) {
		this.tbSiaUrl = tbSiaUrl;
	}

	public String getSelPaginacion() {
		return selPaginacion;
	}

	public void setSelPaginacion(String selPaginacion) {
		this.selPaginacion = selPaginacion;
	}
}