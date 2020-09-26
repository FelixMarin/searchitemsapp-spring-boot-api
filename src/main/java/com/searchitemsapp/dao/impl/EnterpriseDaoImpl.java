package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.EnterpriseDao;
import com.searchitemsapp.dao.repository.EnterpriseRepository;
import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.dto.EnterpriseDto;
import com.searchitemsapp.entities.TbSiaEmpresa;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("unchecked")
@Component
@AllArgsConstructor
public class EnterpriseDaoImpl extends AbstractDao implements EnterpriseDao {

	private EnterpriseRepository repository;
	private Environment env;

	@Override
	public List<EnterpriseDto> findAll() throws IOException {
		List<EnterpriseDto> listDto = Lists.newArrayList();

		repository.findAll()
				.forEach(elem -> listDto.add(getModelMapper().map(elem, EnterpriseDto.class)));

		return listDto;
	}

	@Override
	public EnterpriseDto findByDid(@NonNull final EnterpriseDto empresaDto) throws IOException {
		return getModelMapper().map(repository.findByDid(empresaDto.getDid()), EnterpriseDto.class);
	}

	@Override
	public List<EnterpriseDto> findByTbSia(@NonNull final EnterpriseDto empresaDto, 
			@NonNull final CategoryDto categoriaDto) throws IOException {

		List<EnterpriseDto> listDto = Lists.newArrayList(); 
		
		Query q = getEntityManager().createQuery(env
				.getProperty("flow.value.empresa.select.lista.empresas.by.empresa.y.categoria"));		
		
		q.setParameter(env.getProperty("flow.value.categoria.didEmpresa.key"), empresaDto.getDid());	
		q.setParameter(env.getProperty("flow.value.categoria.didCategoriaEmpresa.key"), categoriaDto.getDid());	
		
		List<TbSiaEmpresa> liEntities = (q.getResultList());
		
		liEntities.forEach(elem ->listDto
				.add(getModelMapper().map(elem, EnterpriseDto.class)));
		
		return listDto;
	}
}
