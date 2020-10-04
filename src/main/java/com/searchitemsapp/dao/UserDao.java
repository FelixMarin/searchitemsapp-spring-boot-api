package com.searchitemsapp.dao;

import java.io.IOException;

import com.searchitemsapp.entities.TbSiaUser;

public interface UserDao {

	abstract TbSiaUser findByUserName(String username);
	abstract TbSiaUser findByDid(Long dto) throws IOException;
	abstract Boolean existById(Long id);
	abstract TbSiaUser save(TbSiaUser entity);
	abstract  void delete(Long id);
}
