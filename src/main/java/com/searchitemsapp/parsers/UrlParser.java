package com.searchitemsapp.parsers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.UrlDTO;
import com.searchitemsapp.entities.TbSiaUrl;
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
public class UrlParser implements IFParser<UrlDTO, TbSiaUrl> {
	
	@Override
	public UrlDTO toDTO(@NotNull final TbSiaUrl tbSiaPUrl) {	
		
		return new ModelMapper().map(tbSiaPUrl, UrlDTO.class);
		
	}
	
	@Override
	public List<UrlDTO> toListDTO(@NotNull final List<TbSiaUrl> lsUrls) {
		
		List<UrlDTO> listDto = Lists.newArrayList(); 
		
		lsUrls.forEach(elem -> {
			listDto.add(new ModelMapper().map(elem, UrlDTO.class));
		});
		
		return listDto;
	}
	
	@Override
	public List<UrlDTO> toListODTO(@NotNull final List<Object[]> urlList) {
		
		List<UrlDTO> listUrlDto = Lists.newArrayList();
		
		if (!urlList.isEmpty()){ 
			
			urlList.forEach(objects -> {
				UrlDTO urlPDto = new UrlDTO();
				urlPDto.setNomUrl(String.valueOf(objects[0]));
				urlPDto.setDidEmpresa(Integer.parseInt(String.valueOf(objects[1])));
				urlPDto.setDid(Integer.parseInt(String.valueOf(objects[2])));
				urlPDto.setBolActivo(Boolean.parseBoolean(null!=objects[3]?String.valueOf(objects[3]):null));
				urlPDto.setNomEmpresa(String.valueOf(objects[4]));
				urlPDto.setBolStatus(Boolean.parseBoolean(null!=objects[5]?String.valueOf(objects[5]):null));
				urlPDto.setBolLogin(Boolean.parseBoolean(null!=objects[6]?String.valueOf(objects[6]):null));
				urlPDto.setDesUrl(String.valueOf(objects[7]));
				listUrlDto.add(urlPDto);				
			});
		}
		return listUrlDto;
	}
}
