package com.searchitemsapp.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.searchitemsapp.entities.TbSiaUser;

public interface UserRepository   extends JpaRepository<TbSiaUser, Long>{

	abstract TbSiaUser findByUsername(String username);
	
}
