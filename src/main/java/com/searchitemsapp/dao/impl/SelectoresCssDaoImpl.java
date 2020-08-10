package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.SelectoresCssDao;
import com.searchitemsapp.dao.repository.SelectoresCssRepository;
import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.entities.TbSiaSelectoresCss;

import lombok.NoArgsConstructor;

@SuppressWarnings("unchecked")
@Component
@NoArgsConstructor
public class SelectoresCssDaoImpl extends AbstractDao implements SelectoresCssDao {
	
	@Autowired
	private SelectoresCssRepository ifSelectoresCssRepository;
	
	@Autowired
	private Environment env;
	
	@Override
	public List<SelectoresCssDTO> findAll() throws IOException {
		List<SelectoresCssDTO> listDto = Lists.newArrayList();

		ifSelectoresCssRepository.findAll()
				.forEach(elem -> listDto.add(getModelMapper().map(elem, SelectoresCssDTO.class)));

		return listDto;
	}	
	
	@Override
	public SelectoresCssDTO findByDid(final SelectoresCssDTO selectorCssDto) throws IOException {
		
		return ifSelectoresCssRepository.findByDid(selectorCssDto.getDid());
	}	
	
	@Override
	public List<SelectoresCssDTO> findByTbSia(final SelectoresCssDTO selectoresCssDto, EmpresaDTO empresaDto) throws IOException {

		List<SelectoresCssDTO> listDto = Lists.newArrayList(); 

		Query q = getEntityManager().createQuery(env
				.getProperty("flow.value.selectorescss.select.by.didEmpresa"), 
				TbSiaSelectoresCss.class);
		
		q.setParameter("didEmpresa", empresaDto.getDid());

		List<TbSiaSelectoresCss> liEntities = (q.getResultList());
		
		liEntities.forEach(elem -> listDto
				.add(getModelMapper().map(elem, SelectoresCssDTO.class)));
		
		return listDto;
	}
}
