package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.CountryDao;
import com.searchitemsapp.dao.repository.CountryRepository;
import com.searchitemsapp.dto.CountryDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CountryDaoImpl extends AbstractDao implements CountryDao {
	
	private CountryRepository countryRepository;

	@Override
	public CountryDto findByDid(final CountryDto country) throws IOException {
		return countryRepository.findByDid(country.getDid());
	}

	@Override
	public List<CountryDto> findAll() throws IOException {
		List<CountryDto> countryList = Lists.newArrayList();

		countryRepository.findAll()
				.forEach(country -> countryList.add(getModelMapper().map(country, CountryDto.class)));

		return countryList;
	}
}
