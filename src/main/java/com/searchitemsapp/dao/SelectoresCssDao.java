package com.searchitemsapp.dao;
import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.repository.IFSelectoresCssRepository;
import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.entities.TbSiaSelectoresCss;

import lombok.NoArgsConstructor;

@SuppressWarnings("unchecked")
@NoArgsConstructor
@Repository
public class SelectoresCssDao extends AbstractDao implements IFSelectoresCssRepository {
	
	@Autowired
	private Environment env;

	@Override
	public List<SelectoresCssDTO> findAll() throws IOException {
		
		List<SelectoresCssDTO> listDto = Lists.newArrayList(); 
			
		Query q = getEntityManager().createQuery(env
				.getProperty("flow.value.selectorescss.select.all"), TbSiaSelectoresCss.class);
		
		List<TbSiaSelectoresCss> liEntities = (q.getResultList());
		
		liEntities.forEach(elem -> listDto
				.add(getModelMapper().map(elem, SelectoresCssDTO.class)));
		
		return listDto;
	}

	@Override
	public SelectoresCssDTO findByDid(final Integer did) throws IOException {
				
		return getModelMapper().map(getEntityManager()
				.find(TbSiaSelectoresCss.class, did), SelectoresCssDTO.class);
	}

	@Override
	public List<SelectoresCssDTO> findByTbSiaEmpresa(
			final Integer didEmpresa) throws IOException {

		List<SelectoresCssDTO> listDto = Lists.newArrayList(); 

		Query q = getEntityManager().createQuery(env
				.getProperty("flow.value.selectorescss.select.by.didEmpresa"), 
				TbSiaSelectoresCss.class);
		
		q.setParameter("didEmpresa", didEmpresa);

		List<TbSiaSelectoresCss> liEntities = (q.getResultList());
		
		liEntities.forEach(elem -> listDto
				.add(getModelMapper().map(elem, SelectoresCssDTO.class)));
		
		return listDto;
	}	
}
