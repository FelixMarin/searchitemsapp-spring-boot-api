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
	
	private CountryRepository ifPaisRepository;

	@Override
	public CountryDto findByDid(final CountryDto paisDto) throws IOException {
		return ifPaisRepository.findByDid(paisDto.getDid());
	}

	@Override
	public List<CountryDto> findAll() throws IOException {
		List<CountryDto> listDto = Lists.newArrayList();

		ifPaisRepository.findAll()
				.forEach(elem -> listDto.add(getModelMapper().map(elem, CountryDto.class)));

		return listDto;
	}
}
