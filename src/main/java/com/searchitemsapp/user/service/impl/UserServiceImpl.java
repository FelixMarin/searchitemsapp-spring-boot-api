package com.searchitemsapp.user.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.searchitemsapp.exception.ConfilctFoundException;
import com.searchitemsapp.exception.ResourceNotFoundException;
import com.searchitemsapp.user.dao.UserDao;
import com.searchitemsapp.user.dto.UserDto;
import com.searchitemsapp.user.service.UserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public boolean save(UserDto user)  throws ConfilctFoundException {
		
		userDao.save(UserDto.builder()
				.username(user.getUsername())
				.password(passwordEncoder.encode(user.getPassword()))
				.accountNonExpired(true)
				.accountNonLocked(true)
				.enabled(true)
				.email(user.getEmail()).build());
		
		return true;
	}

	@Override
	public boolean delete(UserDto user) throws ResourceNotFoundException {
		UserDto userResult = userDao.findByUserName(user.getUsername());
		userDao.delete(userResult.getId());
		return true;
	}

	@Override
	public boolean update(UserDto user) throws ConfilctFoundException, ResourceNotFoundException {
		UserDto userDto = userDao.findByUserName(user.getUsername());
		userDto.setUsername(user.getUsername());
		userDto.setPassword(passwordEncoder.encode(user.getPassword()));
		userDto.setEmail(user.getEmail());
		userDao.save(userDto);
		return true;
	}

	@Override
	public UserDto existsByUserName(String username) throws ResourceNotFoundException {
		UserDto userOut = userDao.findByUserName(username);
		userOut.setRoles(null);
		return userOut;
	}
	
	@Override
	public List<UserDto> findAll() {
		List<UserDto> allUsers = userDao.findAll();		
		allUsers.forEach(elem -> elem.setRoles(null));		
		return allUsers;
	}
}