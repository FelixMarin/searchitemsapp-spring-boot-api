package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.UrlDao;
import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.dto.CountryDto;
import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.entities.TbSiaUrl;
import com.searchitemsapp.exception.ResourceNotFoundException;
import com.searchitemsapp.repository.UrlRepository;
import com.searchitemsapp.resource.Constants;

import lombok.AllArgsConstructor;

@SuppressWarnings("unchecked")
@Component
@AllArgsConstructor
public class UrlDaoImpl extends AbstractDao implements UrlDao {
	
	private UrlRepository repository;
	private Environment environment;
	
	public List<UrlDto> obtenerUrlsPorIdEmpresa(final CountryDto paisDto, 
			final CategoryDto categoriaDto,
			final String idsEmpresas) 
			throws IOException {
		
		var strIdsEmpresas =Constants.ALL.getValue().equalsIgnoreCase(idsEmpresas)?
				environment.getProperty("flow.value.all.id.empresa"):idsEmpresas;
		
		String[] arIdsEpresas = tokenizeString(strIdsEmpresas, Constants.COMMA.getValue());
		List<UrlDto> lsIdsEmpresas = Lists.newArrayList();
		List<UrlDto> listUrlDTO = findByDidAndDesUrl(paisDto.getDid(), categoriaDto.getDid());
		List<String> liIdsEmpresas = Arrays.asList(arIdsEpresas);
		
		liIdsEmpresas.forEach(strId -> 
			listUrlDTO.stream()
			.filter(urlDto -> urlDto.getDidEmpresa() == Integer.parseInt(strId))
			.forEach(lsIdsEmpresas::add));
		
		return lsIdsEmpresas;
	}
	
	private List<UrlDto> findByDidAndDesUrl(final Long didPais, 
			final Long didCategoria) {

		List<UrlDto> listDto = Lists.newArrayList(); 
		
		var q = getEntityManager().createNativeQuery(environment
				.getProperty("flow.value.url.select.url.by.pais.categoria"));	
		
		q.setParameter(environment.getProperty("flow.value.empresa.didCategoria.key"), didCategoria);
		q.setParameter(environment.getProperty("flow.value.categoria.didPais.key"), didPais);
		
		List<UrlDto> listUrlDto = super.toListODTO(q.getResultList());
		
		listUrlDto.forEach(elem -> listDto
				.add(getModelMapper().map(elem, UrlDto.class)));
		
		return listDto;
	}

	@Override
	public Optional<UrlDto> findByDid(final UrlDto urlDTO) throws IOException, ResourceNotFoundException {
		Optional<TbSiaUrl> entity = repository.findById(urlDTO.getDid());
		return Optional.of(getModelMapper().map(entity.orElseThrow(() -> 
				new ResourceNotFoundException("Resource not found")), UrlDto.class));
	}
}
