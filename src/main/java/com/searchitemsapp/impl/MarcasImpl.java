package com.searchitemsapp.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dao.repository.IFMarcasRepository;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.MarcasDTO;

@Component
public class MarcasImpl implements IFImplementacion<MarcasDTO, CategoriaDTO> {
	
	@Autowired
	private IFMarcasRepository marcasDao;
	
	public MarcasImpl() {
		super();
	}

	public List<MarcasDTO> findAll() throws IOException {
		
		return marcasDao.findAll();
	}
	
	@Override
	public MarcasDTO findByDid(final MarcasDTO marcasDTO) throws IOException {
		
		return marcasDao.findByDid(marcasDTO.getDid());		
	}

	@Override
	public List<MarcasDTO> findByTbSia(MarcasDTO r, CategoriaDTO t) throws IOException {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
