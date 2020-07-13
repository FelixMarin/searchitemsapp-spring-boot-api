package com.searchitemsapp.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dao.repository.IFPaisRepository;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.PaisDTO;
import com.sun.istack.NotNull;

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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaisImpl.class);  
	
	@Autowired
	private IFPaisRepository paisDao;
	
	public PaisImpl() {
		super();
	}
	
	/**
	 * Recupera un elemento de la tabla a partir
	 * de su identificador.
	 * 
	 * @param PaisDTO
	 * @return PaisDTO
	 * @exception IOException
	 */
	@Override
	public PaisDTO findByDid(@NotNull final PaisDTO paisDto) throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
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
