package com.searchitemsapp.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name="tb_sia_user", schema = "sia")
@NamedQuery(name="TbSiaUser.findAll", query="SELECT t FROM TbSiaUser t")
public class TbSiaUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id;

	
	private String username;

	private String password;

	private Boolean enabled;
	
	@Column(name="account_non_expired")
	private Boolean accountNonExpired;
	
	@Column(name="account_non_locked")
	private Boolean accountNonLocked;
	
	@Column(name="create_at")
	private LocalDateTime createAt;
	
    @ManyToMany(cascade = { 
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
        name = "tb_sia_user_roles", schema = "sia",
        joinColumns = { @JoinColumn(name = "user_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
   private List<TbSiaRoles> roles = Lists.newArrayList();

    

}