package com.searchitemsapp.parsers;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.PaisDTO;
import com.searchitemsapp.entities.TbSiaPais;

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
public class PaisParser implements IFParser<PaisDTO, TbSiaPais> {

	@Override
	public PaisDTO toDTO(final TbSiaPais tbSiaPPais) {	
		
		return new ModelMapper().map(tbSiaPPais, PaisDTO.class);
		
	}

	@Override
	public List<PaisDTO> toListDTO(final List<TbSiaPais> objeto) {
		
		List<PaisDTO> listDto = Lists.newArrayList(); 
		
		objeto.forEach(elem -> {
			listDto.add(new ModelMapper().map(elem, PaisDTO.class));
		});
		
		return listDto;
	}
	
	@Override
	public List<PaisDTO> toListODTO(final List<Object[]> objeto) {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
