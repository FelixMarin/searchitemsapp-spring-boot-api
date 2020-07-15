package com.searchitemsapp.parsers;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.entities.TbSiaMarcas;


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
public class MarcasParser implements IFParser<MarcasDTO, TbSiaMarcas> {

	@Override
	public MarcasDTO toDTO(final TbSiaMarcas tbSiaMarcas) {
		
		return new ModelMapper().map(tbSiaMarcas, MarcasDTO.class);
		
	}

	@Override
	public List<MarcasDTO> toListDTO(final List<TbSiaMarcas> lsTbSiaMarcas) {
		
		List<MarcasDTO> listDto = Lists.newArrayList(); 
		
		lsTbSiaMarcas.forEach(elem -> {
			listDto.add(new ModelMapper().map(elem, MarcasDTO.class));
		});
		
		return listDto;
	}

	@Override
	public List<MarcasDTO> toListODTO(final List<Object[]> objeto) {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
