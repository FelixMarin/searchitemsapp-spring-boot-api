package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.EmpresaDao;
import com.searchitemsapp.dao.repository.EmpresaRepository;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.entities.TbSiaEmpresa;

import lombok.NoArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("unchecked")
@Component
@NoArgsConstructor
public class EmpresaDaoImpl extends AbstractDao implements EmpresaDao {

	@Autowired
	private EmpresaRepository repository;
	
	@Autowired
	private Environment env;

	@Override
	public List<EmpresaDTO> findAll() throws IOException {
		List<EmpresaDTO> listDto = Lists.newArrayList();

		repository.findAll()
				.forEach(elem -> listDto.add(getModelMapper().map(elem, EmpresaDTO.class)));

		return listDto;
	}

	@Override
	public EmpresaDTO findByDid(@NonNull final EmpresaDTO empresaDto) throws IOException {
		return repository.findByDid(empresaDto.getDid());
	}

	@Override
	public List<EmpresaDTO> findByTbSia(@NonNull final EmpresaDTO empresaDto, @NonNull final CategoriaDTO categoriaDto)
			throws IOException {

		List<EmpresaDTO> listDto = Lists.newArrayList(); 
		
		Query q = getEntityManager().createQuery(env
				.getProperty("flow.value.empresa.select.lista.empresas.by.empresa.y.categoria"));		
		
		q.setParameter(env.getProperty("flow.value.categoria.didEmpresa.key"), empresaDto.getDid());	
		q.setParameter(env.getProperty("flow.value.categoria.didCategoriaEmpresa.key"), categoriaDto.getDid());	
		
		List<TbSiaEmpresa> liEntities = (q.getResultList());
		
		liEntities.forEach(elem ->listDto
				.add(getModelMapper().map(elem, EmpresaDTO.class)));
		
		return listDto;
	}
}
