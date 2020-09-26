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
	
	private BrandsRepository ifMarcasRepository;

	public List<BrandsDto> findAll() throws IOException {
		List<BrandsDto> listDto = Lists.newArrayList();

		ifMarcasRepository.findAll()
				.forEach(elem -> listDto.add(getModelMapper().map(elem, BrandsDto.class)));

		return listDto;
	}
	
	@Override
	public BrandsDto findByDid(final BrandsDto marcasDTO) throws IOException {
		return ifMarcasRepository.findByDid(marcasDTO.getDid());		
	}
}
