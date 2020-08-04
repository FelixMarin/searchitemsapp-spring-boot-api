package com.searchitemsapp.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dao.repository.IFNomProductoRepository;
import com.searchitemsapp.dto.NomProductoDTO;

@Component
public class NomProductoImpl  implements IFImplementacion<NomProductoDTO, Object> {
	
	@Autowired
	private IFNomProductoRepository nomProductoDao;
	
	public NomProductoImpl() {
		super();
	}

	@Override
	public NomProductoDTO findByDid(NomProductoDTO objeto) throws IOException {
			
		return nomProductoDao.findByDid(objeto.getDid());
	}

	@Override
	public List<NomProductoDTO> findAll() throws IOException {
		
		return nomProductoDao.findAll();
	}

	@Override
	public List<NomProductoDTO> findByTbSia(NomProductoDTO r, Object t) throws IOException {
		throw new NotImplementedException("Funcionalidad no implementada");
	}

}
