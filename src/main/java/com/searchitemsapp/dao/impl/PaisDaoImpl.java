package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.PaisDao;
import com.searchitemsapp.dao.repository.PaisRepository;
import com.searchitemsapp.dto.PaisDTO;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class PaisDaoImpl extends AbstractDao implements PaisDao {
	
	@Autowired
	private PaisRepository ifPaisRepository;

	@Override
	public PaisDTO findByDid(final PaisDTO paisDto) throws IOException {
		return ifPaisRepository.findByDid(paisDto.getDid());
	}

	@Override
	public List<PaisDTO> findAll() throws IOException {
		List<PaisDTO> listDto = Lists.newArrayList();

		ifPaisRepository.findAll()
				.forEach(elem -> listDto.add(getModelMapper().map(elem, PaisDTO.class)));

		return listDto;
	}
}
