package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.CompanyDao;
import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.dto.CompanyDto;
import com.searchitemsapp.entities.TbSiaEmpresa;
import com.searchitemsapp.exception.ResourceNotFoundException;
import com.searchitemsapp.repository.CompanyRepository;

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
	public Optional<CompanyDto> findByDid(@NonNull final CompanyDto company) throws IOException, ResourceNotFoundException {
		Optional<TbSiaEmpresa> empresa = repository.findById((company.getDid()));		
		return Optional.of(getModelMapper() 
				.map(empresa.orElseThrow(() -> 
					new ResourceNotFoundException("Resource not found")), CompanyDto.class));
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
		
		liEntities.stream().filter(ObjectUtils::isNotEmpty).forEach(elem ->listDto
				.add(getModelMapper().map(elem, CompanyDto.class)));
		
		return listDto;
	}
}
