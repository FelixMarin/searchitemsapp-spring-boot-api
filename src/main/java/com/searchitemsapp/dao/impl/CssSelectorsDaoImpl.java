package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.CssSelectorsDao;
import com.searchitemsapp.dao.repository.CssSelectorsRepository;
import com.searchitemsapp.dto.EnterpriseDto;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.entities.TbSiaSelectoresCss;

import lombok.AllArgsConstructor;

@SuppressWarnings("unchecked")
@Component
@AllArgsConstructor
public class CssSelectorsDaoImpl extends AbstractDao implements CssSelectorsDao {
	
	private CssSelectorsRepository ifSelectoresCssRepository;
	private Environment env;
	
	@Override
	public List<CssSelectorsDto> findAll() throws IOException {
		List<CssSelectorsDto> listDto = Lists.newArrayList();

		ifSelectoresCssRepository.findAll()
				.forEach(elem -> listDto.add(getModelMapper().map(elem, CssSelectorsDto.class)));

		return listDto;
	}	
	
	@Override
	public CssSelectorsDto findByDid(final CssSelectorsDto selectorCssDto) throws IOException {
		
		return ifSelectoresCssRepository.findByDid(selectorCssDto.getDid());
	}	
	
	@Override
	public List<CssSelectorsDto> findByTbSia(final CssSelectorsDto selectoresCssDto, EnterpriseDto empresaDto) throws IOException {

		List<CssSelectorsDto> listDto = Lists.newArrayList(); 

		Query q = getEntityManager().createQuery(env
				.getProperty("flow.value.selectorescss.select.by.didEmpresa"), 
				TbSiaSelectoresCss.class);
		
		q.setParameter("didEmpresa", empresaDto.getDid());

		List<TbSiaSelectoresCss> liEntities = (q.getResultList());
		
		liEntities.forEach(elem -> listDto
				.add(getModelMapper().map(elem, CssSelectorsDto.class)));
		
		return listDto;
	}
}
