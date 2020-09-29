package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import javax.persistence.Query;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.UrlDao;
import com.searchitemsapp.dao.repository.UrlRepository;
import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.dto.CountryDto;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.entities.TbSiaUrl;
import com.searchitemsapp.resources.Constants;

import lombok.AllArgsConstructor;

@SuppressWarnings("unchecked")
@Component
@AllArgsConstructor
public class UrlDaoImpl extends AbstractDao implements UrlDao {
	
	private UrlRepository repository;
	private Environment environment;
	
	public List<UrlDto> obtenerUrls(CountryDto paisDto, CategoryDto categoriaDto) throws IOException {
			
		return findByDidAndDesUrl(paisDto.getDid(), String.valueOf(categoriaDto.getDid()));
	}
	
	public List<UrlDto> obtenerUrlsLogin(CountryDto paisDto, CategoryDto categoriaDto) throws IOException {
			
		return findByDidAndNomUrl(paisDto.getDid(), String.valueOf(categoriaDto.getDid()));
	}
	
	public List<UrlDto> obtenerUrlsPorIdEmpresa(final CountryDto paisDto, 
			final CategoryDto categoriaDto,
			final String idsEmpresas) 
			throws IOException {
		
		String strIdsEmpresas;
		
		if(Constants.ALL.getValue().equalsIgnoreCase(idsEmpresas)) {
			strIdsEmpresas = environment.getProperty("flow.value.all.id.empresa");
		} else {
			strIdsEmpresas = idsEmpresas;
		}
		
		String[] arIdsEpresas = tokenizeString(strIdsEmpresas, Constants.COMMA.getValue());
		List<UrlDto> lsIdsEmpresas = Lists.newArrayList();
				
		List<UrlDto> listUrlDTO = findByDidAndDesUrl(paisDto.getDid(), String.valueOf(categoriaDto.getDid()));
		
		if(Objects.isNull(listUrlDTO)) {
			return lsIdsEmpresas;
		}
		
		List<String> liIdsEmpresas = Arrays.asList(arIdsEpresas);
		
		liIdsEmpresas.forEach(strId -> {
			listUrlDTO.stream().filter(urlDto -> urlDto.getDidEmpresa() == Integer.parseInt(strId)).forEach(urlDto -> {
				lsIdsEmpresas.add(urlDto);
			});
		});
		
		return lsIdsEmpresas;
	}
	
	private String[] tokenizeString(final String cadena, final String token) {
		
		StringTokenizer tokenizer = new StringTokenizer(cadena, token); 		
		List<String> listaAux = Lists.newArrayList();
		
		while (tokenizer.hasMoreElements()) {
			listaAux.add((String) tokenizer.nextElement());
		}
		
		return listaAux.toArray(new String[0]);
	}

	@Override
	public UrlDto findByDid(final UrlDto urlDTO) throws IOException {
		return repository.findByDid(urlDTO.getDid());
	}
	
	@Override
	public List<UrlDto> findByDidAndDesUrl(final Integer didPais, 
			final String didCategoria) throws IOException {

		List<UrlDto> listDto = Lists.newArrayList(); 
		
		Query q = getEntityManager().createNativeQuery(environment
				.getProperty("flow.value.url.select.url.by.pais.categoria"));	
		
		q.setParameter(environment.getProperty("flow.value.empresa.didCategoria.key"), Integer.parseInt(didCategoria));
		q.setParameter(environment.getProperty("flow.value.categoria.didPais.key"), didPais);
		
		List<UrlDto> listUrlDto = toListODTO(q.getResultList());
		
		listUrlDto.forEach(elem -> listDto
				.add(getModelMapper().map(elem, UrlDto.class)));
		
		return listDto;
	}

	@Override
	public List<UrlDto> findByDidAndNomUrl(
			final Integer didPais, 
			final String didCategoria) 
					throws IOException {

		List<UrlDto> listDto = Lists.newArrayList(); 
		
		Query q = getEntityManager().createNativeQuery(environment
				.getProperty("flow.value.url.select.url.by.bollogin"));
		
		q.setParameter(environment.getProperty("flow.value.empresa.didCategoria.key"), Integer.parseInt(didCategoria));
		q.setParameter(environment.getProperty("flow.value.categoria.didPais.key"), didPais);

		List<TbSiaUrl> liEntities = (q.getResultList());
		
		liEntities.forEach(elem -> listDto
				.add(getModelMapper().map(elem, UrlDto.class)));
		
		return listDto;
	}
}
