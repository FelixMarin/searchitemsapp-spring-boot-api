package com.searchitemsapp.parsers;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.entities.TbSiaSelectoresCss;
import com.sun.istack.NotNull;

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
public class SelectoresCssParser implements IFParser<SelectoresCssDTO, TbSiaSelectoresCss> {

	@Override
	public SelectoresCssDTO toDTO(@NotNull final TbSiaSelectoresCss tbSiaSelectores) {
		
		return new ModelMapper().map(tbSiaSelectores, SelectoresCssDTO.class);
		
	}

	@Override
	public List<SelectoresCssDTO> toListDTO(@NotNull final List<TbSiaSelectoresCss> lsTbSiaSelectoresCss) {
		
		List<SelectoresCssDTO> listDto = Lists.newArrayList(); 
		
		lsTbSiaSelectoresCss.forEach(elem -> {
			listDto.add(new ModelMapper().map(elem, SelectoresCssDTO.class));
		});
		
		return listDto;
	}
	
	@Override
	public List<SelectoresCssDTO> toListODTO(final List<Object[]> objeto) {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
