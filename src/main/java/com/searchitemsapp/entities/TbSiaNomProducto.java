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

import lombok.NoArgsConstructor;

@Entity
@Component
@NoArgsConstructor
@Table(name="tb_sia_nom_productos", schema = "sia")
@NamedQuery(name="TbSiaNomProducto.findAll", query="SELECT t FROM TbSiaNomProducto t")
public class TbSiaNomProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "did")
	private Long did;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_categoria", referencedColumnName="did", nullable = false)
	private TbSiaCategoriasEmpresa tbSiaCategoriasEmpresa;
	
	@Column(name="nom_producto")
	private String nomProducto;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_pais", referencedColumnName="did", nullable = false)
	private TbSiaPais tbSiaPais;

	public Long getDid() {
		return this.did;
	}

	public void setDid(Long did) {
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