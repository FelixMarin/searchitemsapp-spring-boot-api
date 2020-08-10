package com.searchitemsapp.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDTO;


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
	
	protected List<UrlDTO> toListODTO(final List<Object[]> urlList) {
		
		List<UrlDTO> listUrlDto = Lists.newArrayList();
		
		if (!urlList.isEmpty()){ 
			
			urlList.forEach(obj -> {
				UrlDTO urlPDto = new UrlDTO();
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