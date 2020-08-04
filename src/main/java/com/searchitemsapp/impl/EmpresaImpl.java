package com.searchitemsapp.impl;


import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dao.repository.IFEmpresaRepository;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.EmpresaDTO;

@Component
public class EmpresaImpl implements IFImplementacion<EmpresaDTO, CategoriaDTO> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaImpl.class);  

	@Autowired
	private IFEmpresaRepository empresaDao;
	
	public EmpresaImpl() {
		super();
	}

	@Override
	public List<EmpresaDTO> findAll() throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return empresaDao.findAll();
	}	

	@Override
	public EmpresaDTO findByDid(final EmpresaDTO empresaDto) throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		if(Objects.isNull(empresaDto.getDid())) {			
			return new EmpresaDTO();
		}
		
		return empresaDao.findByDid(empresaDto.getDid());
	}

	@Override
	public List<EmpresaDTO> findByTbSia(
			final EmpresaDTO empresaDto, 
			final CategoriaDTO categoriaDto) throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return empresaDao.findByDidAndTbSiaCategoriasEmpresa(empresaDto.getDid(), categoriaDto.getDid());
	}	
}
