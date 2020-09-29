package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.BrandsDao;
import com.searchitemsapp.dao.repository.BrandsRepository;
import com.searchitemsapp.dto.BrandsDto;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BrandsDaoImpl extends AbstractDao implements BrandsDao {
	
	private BrandsRepository repository;

	public List<BrandsDto> findAll() throws IOException {
		List<BrandsDto> listDto = Lists.newArrayList();

		repository.findAll()
				.forEach(brandEntity -> listDto.add(getModelMapper().map(brandEntity, BrandsDto.class)));

		return listDto;
	}
	
	@Override
	public BrandsDto findByDid(final BrandsDto brand) throws IOException {
		return repository.findByDid(brand.getDid());		
	}
}
