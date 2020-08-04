package com.searchitemsapp.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dao.repository.IFCategoriaRepository;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.EmpresaDTO;

@Component
public class CategoriaImpl implements IFImplementacion<CategoriaDTO, EmpresaDTO> {

	@Autowired
	private IFCategoriaRepository categoriaDao;
	
	public CategoriaImpl() {
		super();
	}
	
	@Override
	public List<CategoriaDTO> findAll() throws IOException {
		
		return categoriaDao.findAll();
	}
	
	@Override
	public CategoriaDTO findByDid(final CategoriaDTO categoriaDto)  throws IOException {
		
		return categoriaDao.findByDid(categoriaDto.getDid());
	}
	
	@Override
	public List<CategoriaDTO> findByTbSia(CategoriaDTO categoriaDTO, EmpresaDTO empresaDTO) throws IOException {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
