package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.BrandsDao;
import com.searchitemsapp.dto.BrandsDto;
import com.searchitemsapp.entities.TbSiaMarcas;
import com.searchitemsapp.exception.ResourceNotFoundException;
import com.searchitemsapp.repository.BrandsRepository;

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
	public Optional<BrandsDto> findByDid(final BrandsDto brand) throws IOException, ResourceNotFoundException {
		Optional<TbSiaMarcas> tbSiaMarcas = repository.findById(brand.getDid());
		BrandsDto brandDto = getModelMapper().map(tbSiaMarcas.orElseThrow(() ->  
				new ResourceNotFoundException("Resource not found")), BrandsDto.class);
		return Optional.of(brandDto);
	}
}
