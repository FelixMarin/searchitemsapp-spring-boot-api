package com.searchitemsapp.dao.impl;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.searchitemsapp.dao.UserDao;
import com.searchitemsapp.dao.repository.UserRepository;
import com.searchitemsapp.entities.TbSiaUser;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserDaoImpl extends AbstractDao implements UserDao {

	private UserRepository repository;
	
	public TbSiaUser findByUserName(String username) {
		return repository.findByUsername(username);
	}
	
	@Override
	public TbSiaUser findByDid(Long id) throws IOException {
		return repository.getOne(id);
	}

	@Override
	public Boolean existById(Long id) {
		return repository.existsById(id);
	}
	
	@Override
	public TbSiaUser save(TbSiaUser entity) {
		return repository.save(entity);
	}
	
	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
