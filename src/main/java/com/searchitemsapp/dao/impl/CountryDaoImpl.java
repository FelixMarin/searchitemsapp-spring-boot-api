package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.CountryDao;
import com.searchitemsapp.dto.CountryDto;
import com.searchitemsapp.entities.TbSiaPais;
import com.searchitemsapp.exception.ResourceNotFoundException;
import com.searchitemsapp.repository.CountryRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CountryDaoImpl extends AbstractDao implements CountryDao {
	
	private CountryRepository repository;

	public Optional<CountryDto> findByDid(final CountryDto country) throws IOException, ResourceNotFoundException {
		Optional<TbSiaPais> entity = repository.findById(country.getDid());
		return Optional.of(getModelMapper()
				.map(entity.orElseThrow(() ->  
				new ResourceNotFoundException("Resource not found")),
						CountryDto.class)); 
	}

	@Override
	public List<CountryDto> findAll() throws IOException {
		List<CountryDto> countryList = Lists.newArrayList();

		repository.findAll()
				.forEach(country -> countryList.add(getModelMapper().map(country, CountryDto.class)));

		return countryList;
	}
}
