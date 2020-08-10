package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.MarcasDao;
import com.searchitemsapp.dao.repository.MarcasRepository;
import com.searchitemsapp.dto.MarcasDTO;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class MarcasDaoImpl extends AbstractDao implements MarcasDao {
	
	@Autowired
	private MarcasRepository ifMarcasRepository;

	public List<MarcasDTO> findAll() throws IOException {
		List<MarcasDTO> listDto = Lists.newArrayList();

		ifMarcasRepository.findAll()
				.forEach(elem -> listDto.add(getModelMapper().map(elem, MarcasDTO.class)));

		return listDto;
	}
	
	@Override
	public MarcasDTO findByDid(final MarcasDTO marcasDTO) throws IOException {
		return ifMarcasRepository.findByDid(marcasDTO.getDid());		
	}
}
