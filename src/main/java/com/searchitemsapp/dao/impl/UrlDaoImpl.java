package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.UrlDao;
import com.searchitemsapp.dao.repository.UrlRepository;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.PaisDTO;
import com.searchitemsapp.dto.UrlDTO;
import com.searchitemsapp.entities.TbSiaUrl;

import lombok.NoArgsConstructor;

@SuppressWarnings("unchecked")
@Component
@NoArgsConstructor
public class UrlDaoImpl extends AbstractDao implements UrlDao {
	
	private static final String COMA = ",";
	private static final String ALL = "ALL";

	@Autowired
	private UrlRepository ifUrlRepository;
	
	@Autowired
	private Environment env;
	
	public List<UrlDTO> obtenerUrls(PaisDTO paisDto, CategoriaDTO categoriaDto) throws IOException {
			
		return findByDidAndDesUrl(paisDto.getDid(), String.valueOf(categoriaDto.getDid()));
	}
	
	public List<UrlDTO> obtenerUrlsLogin(PaisDTO paisDto, CategoriaDTO categoriaDto) throws IOException {
			
		return findByDidAndNomUrl(paisDto.getDid(), String.valueOf(categoriaDto.getDid()));
	}
	
	public List<UrlDTO> obtenerUrlsPorIdEmpresa(final PaisDTO paisDto, 
			final CategoriaDTO categoriaDto,
			final String idsEmpresas) 
			throws IOException {
		
		String strIdsEmpresas;
		
		if(ALL.equalsIgnoreCase(idsEmpresas)) {
			strIdsEmpresas = env.getProperty("flow.value.all.id.empresa");
		} else {
			strIdsEmpresas = idsEmpresas;
		}
		
		String[] arIdsEpresas = tokenizeString(strIdsEmpresas, COMA);
		List<UrlDTO> lsIdsEmpresas = Lists.newArrayList();
				
		List<UrlDTO> listUrlDTO = findByDidAndDesUrl(paisDto.getDid(), String.valueOf(categoriaDto.getDid()));
		
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
		
		StringTokenizer st = new StringTokenizer(cadena, token); 		
		List<String> listaAux = Lists.newArrayList();
		
		while (st.hasMoreElements()) {
			listaAux.add((String) st.nextElement());
		}
		
		return listaAux.toArray(new String[0]);
	}

	@Override
	public UrlDTO findByDid(final UrlDTO urlDTO) throws IOException {
		return ifUrlRepository.findByDid(urlDTO.getDid());
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
