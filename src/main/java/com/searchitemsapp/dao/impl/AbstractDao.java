package com.searchitemsapp.dao.impl;

import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ObjectUtils;
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
		
		urlList.stream().filter(ObjectUtils::isNotEmpty).forEach(obj -> 
		
		listUrlDto.add(UrlDto.builder().nomUrl(String.valueOf(obj[0]))
			.didEmpresa(Long.parseLong(String.valueOf(obj[1])))
			.did(Long.parseLong(String.valueOf(obj[2])))
			.bolActivo(Boolean.parseBoolean(null!=obj[3]?String.valueOf(obj[3]):null))
			.nomEmpresa(String.valueOf(obj[4]))
			.bolStatus(Boolean.parseBoolean(null!=obj[5]?String.valueOf(obj[5]):null))
			.bolLogin(Boolean.parseBoolean(null!=obj[6]?String.valueOf(obj[6]):null))
			.desUrl(String.valueOf(obj[7])).build()));
		
		return listUrlDto;
	}
	
	protected String[] tokenizeString(final String cadena, final String token) {
		
		StringTokenizer tokenizer = new StringTokenizer(cadena, token); 		
		List<String> listaAux = Lists.newArrayList();
		
		while (tokenizer.hasMoreElements()) {
			listaAux.add((String) tokenizer.nextElement());
		}
		
		return listaAux.toArray(new String[0]);
	}
}