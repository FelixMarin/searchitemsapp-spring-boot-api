package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.CompanyDao;
import com.searchitemsapp.dao.repository.CompanyRepository;
import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.dto.CompanyDto;
import com.searchitemsapp.entities.TbSiaEmpresa;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("unchecked")
@Component
@AllArgsConstructor
public class CompanyDaoImpl extends AbstractDao implements CompanyDao {

	private CompanyRepository repository;
	private Environment environment;

	@Override
	public List<CompanyDto> findAll() throws IOException {
		List<CompanyDto> listDto = Lists.newArrayList();

		repository.findAll()
				.forEach(elem -> listDto.add(getModelMapper().map(elem, CompanyDto.class)));

		return listDto;
	}

	@Override
	public CompanyDto findByDid(@NonNull final CompanyDto company) throws IOException {
		return getModelMapper().map(repository.findByDid(company.getDid()), CompanyDto.class);
	}

	@Override
	public List<CompanyDto> findByTbSia(@NonNull final CompanyDto empresaDto, 
			@NonNull final CategoryDto categoriaDto) throws IOException {

		List<CompanyDto> listDto = Lists.newArrayList(); 
		
		Query q = getEntityManager().createQuery(environment
				.getProperty("flow.value.empresa.select.lista.empresas.by.empresa.y.categoria"));		
		
		q.setParameter(environment.getProperty("flow.value.categoria.didEmpresa.key"), empresaDto.getDid());	
		q.setParameter(environment.getProperty("flow.value.categoria.didCategoriaEmpresa.key"), categoriaDto.getDid());	
		
		List<TbSiaEmpresa> liEntities = (q.getResultList());
		
		liEntities.forEach(elem ->listDto
				.add(getModelMapper().map(elem, CompanyDto.class)));
		
		return listDto;
	}
}
