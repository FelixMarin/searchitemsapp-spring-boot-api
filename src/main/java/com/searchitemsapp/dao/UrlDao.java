package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import javax.persistence.NoResultException;
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
	public List<UrlDTO> findAll() throws IOException, NoResultException {
		
		List<UrlDTO> listDto = Lists.newArrayList(); 
		
		Query q = getEntityManager().createQuery(env
				.getProperty("flow.value.url.select.all"), TbSiaUrl.class);
		
		List<TbSiaUrl> liEntities = ((List<TbSiaUrl>) q.getResultList());
		
		liEntities.forEach(elem -> {
			listDto.add(getModelMapper().map(elem, UrlDTO.class));
		});
		
		return listDto;
	}

	@Override
	public UrlDTO findByDid(final Integer did) throws IOException, NoResultException {

		return getModelMapper().map(getEntityManager()
				.find(TbSiaUrl.class, did), UrlDTO.class);
	}
	
	@Override
	public List<UrlDTO> findByDidAndDesUrl(final Integer didPais, 
			final String didCategoria) throws IOException, NoResultException {

		List<UrlDTO> listDto = Lists.newArrayList(); 
		
		Query q = getEntityManager().createNativeQuery(env
				.getProperty("flow.value.url.select.url.by.pais.categoria"));	
		
		q.setParameter(env.getProperty("flow.value.empresa.didCategoria.key"), Integer.parseInt(didCategoria));
		q.setParameter(env.getProperty("flow.value.categoria.didPais.key"), didPais);
		
		List<UrlDTO> listUrlDto = toListODTO((List<Object[]>) q.getResultList());
		
		listUrlDto.forEach(elem -> {
			listDto.add(getModelMapper().map(elem, UrlDTO.class));
		});
		
		return listDto;
	}

	@Override
	public List<UrlDTO> findByDidAndNomUrl(
			final Integer didPais, 
			final String didCategoria) 
					throws IOException, NoResultException {

		List<UrlDTO> listDto = Lists.newArrayList(); 
		
		Query q = getEntityManager().createNativeQuery(env
				.getProperty("flow.value.url.select.url.by.bollogin"));
		
		q.setParameter(env.getProperty("flow.value.empresa.didCategoria.key"), Integer.parseInt(didCategoria));
		q.setParameter(env.getProperty("flow.value.categoria.didPais.key"), didPais);

		List<TbSiaUrl> liEntities = ((List<TbSiaUrl>) q.getResultList());
		
		liEntities.forEach(elem -> {
			listDto.add(getModelMapper().map(elem, UrlDTO.class));
		});
		
		return listDto;
	}
	
	private List<UrlDTO> toListODTO(final List<Object[]> urlList) {
		
		List<UrlDTO> listUrlDto = Lists.newArrayList();
		
		if (!urlList.isEmpty()){ 
			
			urlList.forEach(obj -> {
				UrlDTO urlPDto = new UrlDTO();
				urlPDto.setNomUrl(String.valueOf(obj[0]));
				urlPDto.setDidEmpresa(Integer.parseInt(String.valueOf(obj[1])));
				urlPDto.setDid(Integer.parseInt(String.valueOf(obj[2])));
				urlPDto.setBolActivo(Boolean.parseBoolean(null!=obj[3]?String.valueOf(obj[3]):null));
				urlPDto.setNomEmpresa(String.valueOf(obj[4]));
				urlPDto.setBolStatus(Boolean.parseBoolean(null!=obj[5]?String.valueOf(obj[5]):null));
				urlPDto.setBolLogin(Boolean.parseBoolean(null!=obj[6]?String.valueOf(obj[6]):null));
				urlPDto.setDesUrl(String.valueOf(obj[7]));
				listUrlDto.add(urlPDto);				
			});
		}
		return listUrlDto;
	}
}
