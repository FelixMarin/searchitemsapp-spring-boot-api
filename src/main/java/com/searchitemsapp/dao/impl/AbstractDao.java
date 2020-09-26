package com.searchitemsapp.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDto;


public abstract class AbstractDao {
	
	private static final ModelMapper MODEL_MAPPER = new ModelMapper();
	
	@PersistenceContext
	private EntityManager entityManager;
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	protected static ModelMapper getModelMapper() {
		return MODEL_MAPPER;
	}
	
	protected List<UrlDto> toListODTO(final List<Object[]> urlList) {
		
		List<UrlDto> listUrlDto = Lists.newArrayList();
		
		if (!urlList.isEmpty()){ 
			
			urlList.forEach(obj -> {
				UrlDto urlPDto = new UrlDto();
				urlPDto.setNomUrl(String.valueOf(obj[0]));
				urlPDto.setDidEmpresa(Integer.parseInt(String.valueOf(obj[1])));
				urlPDto.setDid(Integer.parseInt(String.valueOf(obj[2])));
				urlPDto.setBolActivo(Boolean.parseBoolean(null!=obj[3]?String.valueOf(obj[3]):null));
				urlPDto.setNomEmpresa(String.valueOf(obj[4]));
				urlPDto.setBolStatus(Boolean.parseBoolean(null!=obj[5]?String.valueOf(obj[5]):null));
				urlPDto.setBolLogin(Boolean.parseBoolean(null!=obj[6]?String.valueOf(obj[6]):null));
				urlPDto.setDesUrl(String.valueOf(obj[7]));
				listUrlDto.add(urlPDto);				
			});
		}
		return listUrlDto;
	}
}