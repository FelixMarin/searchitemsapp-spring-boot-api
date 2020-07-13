package com.searchitemsapp.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dao.repository.IFMarcasRepository;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.MarcasDTO;
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
public class MarcasImpl implements IFImplementacion<MarcasDTO, CategoriaDTO> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MarcasImpl.class);  
	
	@Autowired
	private IFMarcasRepository marcasDao;
	
	public MarcasImpl() {
		super();
	}
	
	/**
	 * Recupera todos los elementos de la tabla,
	 * los agrega a una lista y son devueltos.
	 * 
	 * @return List<MarcasDTO>
	 * @exception IOException
	 */
	public List<MarcasDTO> findAll() throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return marcasDao.findAll();
	}
	
	/**
	 * Recupera un elemento de la tabla a partir
	 * de su identificador.
	 * 
	 * @param MarcasDTO
	 * @return MarcasDTO
	 * @exception IOException
	 */
	@Override
	public MarcasDTO findByDid(@NotNull final MarcasDTO marcasDTO) throws IOException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return marcasDao.findByDid(marcasDTO.getDid());		
	}

	@Override
	public List<MarcasDTO> findByTbSia(MarcasDTO r, CategoriaDTO t) throws IOException {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
