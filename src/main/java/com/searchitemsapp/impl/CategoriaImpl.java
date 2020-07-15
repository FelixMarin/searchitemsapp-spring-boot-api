package com.searchitemsapp.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dao.repository.IFCategoriaRepository;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.EmpresaDTO;



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
public class CategoriaImpl implements IFImplementacion<CategoriaDTO, EmpresaDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaImpl.class);  
	
	@Autowired
	private IFCategoriaRepository categoriaDao;
	
	public CategoriaImpl() {
		super();
	}
	
	@Override
	public List<CategoriaDTO> findAll() throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return categoriaDao.findAll();
	}
	
	@Override
	public CategoriaDTO findByDid(final CategoriaDTO categoriaDto)  throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return categoriaDao.findByDid(categoriaDto.getDid());
	}
	
	@Override
	public List<CategoriaDTO> findByTbSia(CategoriaDTO categoriaDTO, EmpresaDTO empresaDTO) throws IOException {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
