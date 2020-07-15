package com.searchitemsapp.parsers;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.NomProductoDTO;
import com.searchitemsapp.entities.TbSiaNomProducto;

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
public class NomProductoParser implements IFParser<NomProductoDTO, TbSiaNomProducto> {

	@Override
	public NomProductoDTO toDTO(final TbSiaNomProducto tbSiaNomProducto) {
		
		return new ModelMapper().map(tbSiaNomProducto, NomProductoDTO.class);
		
	}
	@Override
	public List<NomProductoDTO> toListDTO(final List<TbSiaNomProducto> lsTbSiaNomProducto) {
		
		List<NomProductoDTO> listDto = Lists.newArrayList(); 
		
		lsTbSiaNomProducto.forEach(elem -> {
			listDto.add(new ModelMapper().map(elem, NomProductoDTO.class));
		});
		
		return listDto;
	}
	
	@Override
	public List<NomProductoDTO> toListODTO(List<Object[]> objeto) {
		throw new NotImplementedException("Funcionalidad no implementada");	
	}
}
