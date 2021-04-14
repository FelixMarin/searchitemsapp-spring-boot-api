package com.searchitemsapp.user.service;

import java.util.List;

import com.searchitemsapp.exception.ConfilctFoundException;
import com.searchitemsapp.exception.ResourceNotFoundException;
import com.searchitemsapp.user.dto.UserDto;

public interface UserService {

	abstract boolean save(UserDto user)  throws ConfilctFoundException;
	abstract boolean delete(UserDto user) throws ResourceNotFoundException;
	abstract boolean update(UserDto user) throws ResourceNotFoundException, ConfilctFoundException;
	abstract UserDto existsByUserName(String username) throws ResourceNotFoundException;
	abstract List<UserDto> findAll();
	abstract boolean mailExists(String mail) throws ResourceNotFoundException;
}
           