package com.searchitemsapp.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Component
@NoArgsConstructor
@Table(name="tb_sia_roles", schema = "sia")
@NamedQuery(name="TbSiaRoles.findAll", query="SELECT t FROM TbSiaRoles t")
public class TbSiaRoles implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name="role_name")
	private String roleName;
 
	@Column(name="create_at")
	private LocalDateTime createAt;
	
	@ManyToMany(mappedBy = "roles", cascade = { CascadeType.ALL })
    private List<TbSiaUser> users = Lists.newArrayList();
}