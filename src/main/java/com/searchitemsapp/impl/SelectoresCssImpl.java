package com.searchitemsapp.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dao.repository.IFSelectoresCssRepository;
import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.dto.SelectoresCssDTO;

@Component
public class SelectoresCssImpl implements IFImplementacion<SelectoresCssDTO, EmpresaDTO> {

	
	@Autowired
	private IFSelectoresCssRepository selectoresCssDao;
	
	public SelectoresCssImpl() {
		super();
	}
	
	@Override
	public List<SelectoresCssDTO> findAll() throws IOException {
		
		return selectoresCssDao.findAll();
	}	
	
	@Override
	public SelectoresCssDTO findByDid(final SelectoresCssDTO selectorCssDto) throws IOException {
		
		return selectoresCssDao.findByDid(selectorCssDto.getDid());
	}	
	
	@Override
	public List<SelectoresCssDTO> findByTbSia(final SelectoresCssDTO selectoresCssDto, EmpresaDTO empresaDto) throws IOException {
		
		return selectoresCssDao.findByTbSiaEmpresa(empresaDto.getDid());
	}
}
