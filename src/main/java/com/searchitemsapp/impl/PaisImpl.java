package com.searchitemsapp.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dao.repository.IFPaisRepository;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.PaisDTO;



/**
 * Implementación del dao.
 * 
 * Esta clase ofrece los métodos que permiten interactuar con
 * la capa de persistencia. 
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public class PaisImpl implements IFImplementacion<PaisDTO, CategoriaDTO> {
	
	@Autowired
	private IFPaisRepository paisDao;
	
	public PaisImpl() {
		super();
	}

	@Override
	public PaisDTO findByDid(final PaisDTO paisDto) throws IOException {
		
		return paisDao.findByDid(paisDto.getDid());
	}

	@Override
	public List<PaisDTO> findAll() throws IOException {
		throw new NotImplementedException("Funcionalidad no implementada");
	}

	@Override
	public List<PaisDTO> findByTbSia(PaisDTO paisDTO, CategoriaDTO categoriaDTO) throws IOException {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
