package com.searchitemsapp.impl;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dao.repository.IFEmpresaRepository;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.EmpresaDTO;

import lombok.NoArgsConstructor;
import lombok.NonNull;

@Component
@NoArgsConstructor
public class EmpresaImpl implements IFImplementacion<EmpresaDTO, CategoriaDTO> {
	
	@Autowired
	private IFEmpresaRepository empresaDao;

	@Override
	public List<EmpresaDTO> findAll() throws IOException {
		
		return empresaDao.findAll();
	}	

	@Override
	public EmpresaDTO findByDid(@NonNull final EmpresaDTO empresaDto) throws IOException {
		
		return empresaDao.findByDid(empresaDto.getDid());
	}

	@Override
	public List<EmpresaDTO> findByTbSia(
			@NonNull final EmpresaDTO empresaDto, 
			@NonNull final CategoriaDTO categoriaDto) throws IOException {
		
		return empresaDao.findByDidAndTbSiaCategoriasEmpresa(empresaDto.getDid(), categoriaDto.getDid());
	}	
}
