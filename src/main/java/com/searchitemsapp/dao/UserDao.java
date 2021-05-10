package com.searchitemsapp.dao;

import java.util.List;

import com.searchitemsapp.dto.UserDto;
import com.searchitemsapp.exception.ConfilctFoundException;
import com.searchitemsapp.exception.ResourceNotFoundException;

import lombok.NonNull;

public interface UserDao {

	abstract UserDto findByUserName(String username) throws ResourceNotFoundException;
	abstract Boolean existById(Long id);
	abstract UserDto save(UserDto user)  throws ConfilctFoundException;
	abstract  void delete(Long id);
	abstract List<UserDto> findAll();
	abstract UserDto findByUsernameAndPassword(@NonNull String username, @NonNull String password)  throws ResourceNotFoundException; 
	abstract UserDto findByEmail(String email);
}
