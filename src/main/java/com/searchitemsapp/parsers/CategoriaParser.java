package com.searchitemsapp.parsers;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.entities.TbSiaCategoriasEmpresa;
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
public class CategoriaParser implements IFParser<CategoriaDTO, TbSiaCategoriasEmpresa>  {
	
	@Override
	public CategoriaDTO toDTO(@NotNull final TbSiaCategoriasEmpresa tbSiaPCategorias) {	
		
		return new ModelMapper().map(tbSiaPCategorias, CategoriaDTO.class);
		
	}
	
	@Override
	public List<CategoriaDTO> toListDTO(@NotNull final List<TbSiaCategoriasEmpresa> lsCategorias) {
		
		List<CategoriaDTO> liDto = Lists.newArrayList();
		
		lsCategorias.forEach(elem -> {
			liDto.add(new ModelMapper().map(elem, CategoriaDTO.class));
		});
		
		return liDto;
	}

	@Override
	public List<CategoriaDTO> toListODTO(final List<Object[]> objeto) {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
