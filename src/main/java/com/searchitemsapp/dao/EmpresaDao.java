package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.repository.IFEmpresaRepository;
import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.entities.TbSiaEmpresa;

import lombok.NoArgsConstructor;

@SuppressWarnings("unchecked")
@NoArgsConstructor
@Repository
public class EmpresaDao extends AbstractDao implements IFEmpresaRepository {
	
	@Autowired
	private Environment env;

	@Override
	public List<EmpresaDTO> findAll() throws IOException {
		
		List<EmpresaDTO> listDto = Lists.newArrayList(); 
		
		Query q = getEntityManager().createQuery(env
				.getProperty("flow.value.empresa.select.all"), TbSiaEmpresa.class);
		
		List<TbSiaEmpresa> liEntities = (q.getResultList());
		
		liEntities.forEach(elem -> {
			listDto.add(getModelMapper().map(elem, EmpresaDTO.class));
		});
		
		return listDto;
	}

	@Override
	public EmpresaDTO findByDid(final Integer did) throws IOException {
		
		return getModelMapper().map((getEntityManager()
				.find(TbSiaEmpresa.class, did)), EmpresaDTO.class);
	}

	@Override
	public List<EmpresaDTO> findByDidAndTbSiaCategoriasEmpresa(
			final Integer didEmpresa, 
			final Integer didCatEmpresa) 
					throws IOException {
		
		List<EmpresaDTO> listDto = Lists.newArrayList(); 
	
		Query q = getEntityManager().createQuery(env
				.getProperty("flow.value.empresa.select.lista.empresas.by.empresa.y.categoria"));		
		
		q.setParameter(env.getProperty("flow.value.categoria.didEmpresa.key"), didEmpresa);	
		q.setParameter(env.getProperty("flow.value.categoria.didCategoriaEmpresa.key"), didCatEmpresa);	
		
		List<TbSiaEmpresa> liEntities = (q.getResultList());
		
		liEntities.forEach(elem -> {
			listDto.add(getModelMapper().map(elem, EmpresaDTO.class));
		});
		
		return listDto;
	}
}
