package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.CssSelectorsDao;
import com.searchitemsapp.dto.CompanyDto;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.entities.TbSiaSelectoresCss;
import com.searchitemsapp.exception.ResourceNotFoundException;
import com.searchitemsapp.repository.CssSelectorsRepository;

import lombok.AllArgsConstructor;

@SuppressWarnings("unchecked")
@Component
@AllArgsConstructor
public class CssSelectorsDaoImpl extends AbstractDao implements CssSelectorsDao {
	
	private CssSelectorsRepository repository;
	private Environment environment;
	
	@Override
	public List<CssSelectorsDto> findAll() throws IOException {
		List<CssSelectorsDto> listDto = Lists.newArrayList();

		repository.findAll()
				.forEach(elem -> listDto.add(getModelMapper().map(elem, CssSelectorsDto.class)));

		return listDto;
	}	
	
	@Override
	public Optional<CssSelectorsDto> findByDid(CssSelectorsDto selectorCssDto) throws IOException, ResourceNotFoundException {
		Optional<TbSiaSelectoresCss> cssSelector = repository.findById(selectorCssDto.getDid());
		return Optional.of(getModelMapper().map(cssSelector.orElseThrow(() -> 
			new ResourceNotFoundException("Resource not found")), CssSelectorsDto.class)); 
	}
	
	@Override
	public List<CssSelectorsDto> findByTbSia(CompanyDto empresaDto) throws IOException {

		List<CssSelectorsDto> listDto = Lists.newArrayList(); 

		Query q = getEntityManager().createQuery(environment
				.getProperty("flow.value.selectorescss.select.by.didEmpresa"), 
				TbSiaSelectoresCss.class);
		
		q.setParameter("didEmpresa", empresaDto.getDid());

		List<TbSiaSelectoresCss> liEntities = (q.getResultList());
		
		liEntities.forEach(elem -> listDto
				.add(getModelMapper().map(elem, CssSelectorsDto.class)));
		
		return listDto;
	}
}
