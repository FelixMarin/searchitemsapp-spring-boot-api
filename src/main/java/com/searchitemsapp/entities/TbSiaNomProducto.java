package com.searchitemsapp.entities;

import java.io.Serializable;
import javax.persistence.*;

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
 * The persistent class for the tb_sia_nom_productos database table.
 * 
 *  @author Felix Marin Ramirez
 */
@Entity
@Component
@Table(name="tb_sia_nom_productos", schema = "sia")
@NamedQuery(name="TbSiaNomProducto.findAll", query="SELECT t FROM TbSiaNomProducto t")
public class TbSiaNomProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "did")
	@org.hibernate.annotations.Type(type="didDef")
	private Integer did;

	@Column(name="nom_producto")
	@org.hibernate.annotations.Type(type="strDef")
	private String nomProducto;

	//bi-directional many-to-one association to TbSiaCategoriasEmpresa
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_categoria", referencedColumnName="did", nullable = false)
	private TbSiaCategoriasEmpresa tbSiaCategoriasEmpresa;

	//bi-directional many-to-one association to TbSiaPais
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_pais", referencedColumnName="did", nullable = false)
	private TbSiaPais tbSiaPais;

	public TbSiaNomProducto() {
		super();
	}

	public Integer getDid() {
		return this.did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public String getNomProducto() {
		return this.nomProducto;
	}

	public void setNomProducto(String nomProducto) {
		this.nomProducto = nomProducto;
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