package com.searchitemsapp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.UserDao;
import com.searchitemsapp.entities.TbSiaRoles;
import com.searchitemsapp.entities.TbSiaUser;
import com.searchitemsapp.exception.ResourceNotFoundException;
import com.searchitemsapp.dto.UserDto;
import com.searchitemsapp.repository.RolesRepository;
import com.searchitemsapp.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Component
@AllArgsConstructor
public class UserDaoImpl extends AbstractDao implements UserDao {

	private UserRepository repository;
	private RolesRepository rolesRepository;
	
	@Override
	public UserDto findByUserName(@NonNull String username) throws ResourceNotFoundException {
		var entity= repository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Doesn't exist: " + username));
			  	     
		return UserDto.builder().username(entity.getUsername())
				.password(entity.getPassword())
				.email(entity.getEmail())
				.accountNonExpired(entity.getAccountNonExpired())
				.accountNonLocked(entity.getAccountNonExpired())
				.createAt(entity.getCreateAt())
				.enabled(entity.getEnabled())
				.id(entity.getId())
				.roles(entity.getRoles()).build();
	}
	
	@Override
	public Boolean existById(Long id) {
		return repository.existsById(id);
	}
	
	@Override
	public UserDto save(UserDto user) {
	
		List<TbSiaRoles> roles = Lists.newArrayList();
		roles.add(rolesRepository.getOne(2l));
		user.setRoles(roles);
		var entity = getModelMapper().map(user, TbSiaUser.class); 
		
		repository.saveAndFlush(entity);
		
		return UserDto.builder().username(entity.getUsername())
				.password(entity.getPassword())
				.email(entity.getEmail())
				.accountNonExpired(entity.getAccountNonExpired())
				.accountNonLocked(entity.getAccountNonExpired())
				.createAt(entity.getCreateAt())
				.enabled(entity.getEnabled())
				.id(entity.getId())
				.roles(entity.getRoles()).build();
		
	}
	
	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	@Override
	public List<UserDto> findAll() {
		
		List<TbSiaUser> entities = repository.findAll();
		List<UserDto> users = Lists.newArrayList();
		
		entities.forEach(entity -> users.add(UserDto.builder().username(entity.getUsername())
				.password(entity.getPassword())
				.email(entity.getEmail())
				.accountNonExpired(entity.getAccountNonExpired())
				.accountNonLocked(entity.getAccountNonExpired())
				.createAt(entity.getCreateAt())
				.enabled(entity.getEnabled())
				.id(entity.getId())
				.roles(entity.getRoles()).build()));
		
		return users;
	}

	@Override
	public UserDto findByUsernameAndPassword(@NonNull String username, @NonNull String password) throws ResourceNotFoundException {		
		var entity = repository.findByUsernameAndPassword(username, password)
				.orElseThrow(() -> new ResourceNotFoundException("Doesn't exist: " + username));
		return UserDto.builder().username(entity.getUsername())
				.password(entity.getPassword())
				.email(entity.getEmail())
				.accountNonExpired(entity.getAccountNonExpired())
				.accountNonLocked(entity.getAccountNonExpired())
				.createAt(entity.getCreateAt())
				.enabled(entity.getEnabled())
				.id(entity.getId())
				.roles(entity.getRoles()).build();
	}

	@Override
	public UserDto findByEmail(String email) {

		List<TbSiaUser> list = repository.findByEmail(email);
		
		if(!list.isEmpty()) {
			TbSiaUser entity = list.get(0);
			return  UserDto.builder().username(entity.getUsername())
					.password(entity.getPassword())
					.email(entity.getEmail())
					.accountNonExpired(entity.getAccountNonExpired())
					.accountNonLocked(entity.getAccountNonExpired())
					.createAt(entity.getCreateAt())
					.enabled(entity.getEnabled())
					.id(entity.getId())
					.roles(entity.getRoles()).build();
		} else {
			return UserDto.builder().build();
		}
		
	}
}
