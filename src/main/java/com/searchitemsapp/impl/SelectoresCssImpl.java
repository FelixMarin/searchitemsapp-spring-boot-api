package com.searchitemsapp.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dao.repository.IFSelectoresCssRepository;
import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.dto.SelectoresCssDTO;
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
public class SelectoresCssImpl implements IFImplementacion<SelectoresCssDTO, EmpresaDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SelectoresCssImpl.class);  
	
	@Autowired
	private IFSelectoresCssRepository selectoresCssDao;
	
	public SelectoresCssImpl() {
		super();
	}
	
	/**
	 * Recupera todos los elementos de la tabla,
	 * los agrega a una lista y son devueltos.
	 *  
	 * @return List<SelectoresCssDTO>
	 * @exception IOException
	 */
	@Override
	public List<SelectoresCssDTO> findAll() throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return selectoresCssDao.findAll();
	}	
	
	/**
	 * Recupera un elemento de la tabla a partir
	 * de su identificador.
	 * 
	 * @param SelectoresCssDTO
	 * @return SelectoresCssDTO
	 * @exception IOException
	 */
	@Override
	public SelectoresCssDTO findByDid(@NotNull final SelectoresCssDTO selectorCssDto) throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return selectoresCssDao.findByDid(selectorCssDto.getDid());
	}	
	
	/**
	 * Recupera una lista de objetos {@link SelectoresCssDTO} en formato entidad.
	 * 
	 * @param SelectoresCssDTO
	 * @param EmpresaDTO
	 * @return List<SelectoresCssDTO>
	 * @exception IOException
	 */
	@Override
	public List<SelectoresCssDTO> findByTbSia(@NotNull final SelectoresCssDTO selectoresCssDto, EmpresaDTO empresaDto) throws IOException {
			
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return selectoresCssDao.findByTbSiaEmpresa(empresaDto.getDid());
	}
}
