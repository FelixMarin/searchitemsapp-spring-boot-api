package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.repository.IFUrlRepository;
import com.searchitemsapp.dto.UrlDTO;
import com.searchitemsapp.entities.TbSiaUrl;

import lombok.NoArgsConstructor;

@SuppressWarnings("unchecked")
@NoArgsConstructor
@Repository
public class UrlDao extends AbstractDao implements IFUrlRepository {
	
	@Autowired
	private Environment env;

	@Override
	public List<UrlDTO> findAll() throws IOException {
		
		List<UrlDTO> listDto = Lists.newArrayList(); 
		
		Query q = getEntityManager().createQuery(env
				.getProperty("flow.value.url.select.all"), TbSiaUrl.class);
		
		List<TbSiaUrl> liEntities = (q.getResultList());
		
		liEntities.forEach(elem -> listDto
				.add(getModelMapper().map(elem, UrlDTO.class)));
		
		return listDto;
	}

	@Override
	public UrlDTO findByDid(final Integer did) throws IOException {

		return getModelMapper().map(getEntityManager()
				.find(TbSiaUrl.class, did), UrlDTO.class);
	}
	
	@Override
	public List<UrlDTO> findByDidAndDesUrl(final Integer didPais, 
			final String didCategoria) throws IOException {

		List<UrlDTO> listDto = Lists.newArrayList(); 
		
		Query q = getEntityManager().createNativeQuery(env
				.getProperty("flow.value.url.select.url.by.pais.categoria"));	
		
		q.setParameter(env.getProperty("flow.value.empresa.didCategoria.key"), Integer.parseInt(didCategoria));
		q.setParameter(env.getProperty("flow.value.categoria.didPais.key"), didPais);
		
		List<UrlDTO> listUrlDto = toListODTO(q.getResultList());
		
		listUrlDto.forEach(elem -> listDto
				.add(getModelMapper().map(elem, UrlDTO.class)));
		
		return listDto;
	}

	@Override
	public List<UrlDTO> findByDidAndNomUrl(
			final Integer didPais, 
			final String didCategoria) 
					throws IOException {

		List<UrlDTO> listDto = Lists.newArrayList(); 
		
		Query q = getEntityManager().createNativeQuery(env
				.getProperty("flow.value.url.select.url.by.bollogin"));
		
		q.setParameter(env.getProperty("flow.value.empresa.didCategoria.key"), Integer.parseInt(didCategoria));
		q.setParameter(env.getProperty("flow.value.categoria.didPais.key"), didPais);

		List<TbSiaUrl> liEntities = (q.getResultList());
		
		liEntities.forEach(elem -> listDto
				.add(getModelMapper().map(elem, UrlDTO.class)));
		
		return listDto;
	}
}
