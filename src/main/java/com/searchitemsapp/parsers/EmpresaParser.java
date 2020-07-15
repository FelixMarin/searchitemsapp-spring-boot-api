package com.searchitemsapp.parsers;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.entities.TbSiaEmpresa;


import lombok.NoArgsConstructor;

/**
 * Es un componente analizador de software que 
 * toma datos de entrada y construye una 
 * estructura de datos. 
 * 
 * @author Felix Marin Ramirez
 *
 */
@NoArgsConstructor
@Component
public class EmpresaParser implements IFParser<EmpresaDTO, TbSiaEmpresa> {
	
	@Override
	public EmpresaDTO toDTO(final TbSiaEmpresa tbSiaPEmpresas) {	
		
		return new ModelMapper().map(tbSiaPEmpresas, EmpresaDTO.class);
		
	}
	
	@Override
	public List<EmpresaDTO> toListDTO(final List<TbSiaEmpresa> lsEmpresas) {
			
		List<EmpresaDTO> listDto = Lists.newArrayList(); 
		
		lsEmpresas.forEach(elem -> {
			listDto.add(new ModelMapper().map(elem, EmpresaDTO.class));
		});
		
		return listDto;
	}
	
	@Override
	public List<EmpresaDTO> toListODTO(final List<Object[]> objeto) {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
