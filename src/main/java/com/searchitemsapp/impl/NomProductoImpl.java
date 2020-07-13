package com.searchitemsapp.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dao.repository.IFNomProductoRepository;
import com.searchitemsapp.dto.NomProductoDTO;


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
public class NomProductoImpl  implements IFImplementacion<NomProductoDTO, Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NomProductoImpl.class);  
	
	@Autowired
	private IFNomProductoRepository nomProductoDao;
	
	public NomProductoImpl() {
		super();
	}

	@Override
	public NomProductoDTO findByDid(NomProductoDTO objeto) throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return nomProductoDao.findByDid(objeto.getDid());
	}

	@Override
	public List<NomProductoDTO> findAll() throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return nomProductoDao.findAll();
	}

	@Override
	public List<NomProductoDTO> findByTbSia(NomProductoDTO r, Object t) throws IOException {
		throw new NotImplementedException("Funcionalidad no implementada");
	}

}
