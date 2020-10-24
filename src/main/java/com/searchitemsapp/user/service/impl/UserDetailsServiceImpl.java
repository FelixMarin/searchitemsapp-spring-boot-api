package com.searchitemsapp.user.service.impl;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.searchitemsapp.exception.ResourceNotFoundException;
import com.searchitemsapp.user.dao.UserDao;
import com.searchitemsapp.user.dto.UserDto;

@Service
@Transactional(value = TxType.REQUIRES_NEW)
@Qualifier("sia.users")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired 
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) {
		
		try {
			UserDto user = userDao.findByUserName(username);
			List<GrantedAuthority> authorities = Lists.newArrayList();		
			
			user.getRoles().forEach(role -> 
				authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));

			return new User(user.getUsername(), user.getPassword(),	user.getEnabled(), user.getAccountNonExpired(), 
					true, user.getAccountNonLocked(), authorities);
			
		} catch (ResourceNotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage(),e);
		}
	}
}
