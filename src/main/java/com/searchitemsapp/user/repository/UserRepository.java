package com.searchitemsapp.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.searchitemsapp.entities.TbSiaUser;

import lombok.NonNull;

public interface UserRepository   extends JpaRepository<TbSiaUser, Long>{

	abstract Optional<TbSiaUser> findByUsername(@NonNull String username);
	
	@Query("from TbSiaUser u where u.username = ?1 and u.password = ?2")
	abstract Optional<TbSiaUser> findByUsernameAndPassword(@NonNull String username, @NonNull String password);
	
}
