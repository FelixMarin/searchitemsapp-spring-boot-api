package com.searchitemsapp.parsers;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.entities.TbSiaEmpresa;
import com.searchitemsapp.entities.TbSiaSelectoresCss;
import com.searchitemsapp.entities.TbSiaUrl;
import com.sun.istack.NotNull;

/**
 * Es un componente analizador de software que 
 * toma datos de entrada y construye una 
 * estructura de datos. 
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public class SelectoresCssParser implements IFParser<SelectoresCssDTO, TbSiaSelectoresCss> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SelectoresCssParser.class);  
	
	@Autowired 
	private SelectoresCssDTO selectoresCssDTO;
	
	@Autowired
	private TbSiaSelectoresCss tbSiaSelectoresCss;
	
	public SelectoresCssParser() {
		super();
	}
	
	/**
	 * Mapea los datos de un objeto de tipo Entity a un objeto de tipo DTO.
	 * 
	 * @param TbSiaSelectoresCss
	 * @return SelectoresCssDTO
	 */
	@Override
	public SelectoresCssDTO toDTO(@NotNull final TbSiaSelectoresCss tbSiaSelectores) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		selectoresCssDTO.setDid(tbSiaSelectores.getDid());
		selectoresCssDTO.setBolActivo(tbSiaSelectores.getBolActivo());
		selectoresCssDTO.setFecModificacion(tbSiaSelectores.getFecModificacion());
		selectoresCssDTO.setScrapNoPattern(tbSiaSelectores.getScrapNoPattern());
		selectoresCssDTO.setScrapPattern(tbSiaSelectores.getScrapPattern());
		selectoresCssDTO.setSelImage(tbSiaSelectores.getSelImage());
		selectoresCssDTO.setSelLinkProd(tbSiaSelectores.getSelLinkProd());
		selectoresCssDTO.setSelPrecio(tbSiaSelectores.getSelPrecio());
		selectoresCssDTO.setSelPreKilo(tbSiaSelectores.getSelPreKilo());
		selectoresCssDTO.setSelProducto(tbSiaSelectores.getSelProducto());
		selectoresCssDTO.setDidEmpresa(tbSiaSelectores.getTbSiaEmpresa().getDid());
		selectoresCssDTO.setNomEmpresa(tbSiaSelectores.getTbSiaEmpresa().getNomEmpresa());		
		selectoresCssDTO.setDidUrl(tbSiaSelectores.getTbSiaUrl().getDid());
		selectoresCssDTO.setNomUrl(tbSiaSelectores.getTbSiaUrl().getNomUrl());
		selectoresCssDTO.setSelPaginacion(tbSiaSelectores.getSelPaginacion());
		
		return selectoresCssDTO;
	}
	
	/**
	 * Mapea los datos de un objeto de tipo DTO a un objeto de tipo Entity.
	 * 
	 * @param SelectoresCssDTO
	 * @return TbSiaSelectoresCss
	 */
	@Override
	public TbSiaSelectoresCss toTbSia(@NotNull final SelectoresCssDTO selectoresCssDTO) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		tbSiaSelectoresCss.setDid(selectoresCssDTO.getDid());
		tbSiaSelectoresCss.setBolActivo(selectoresCssDTO.getBolActivo());
		tbSiaSelectoresCss.setFecModificacion(selectoresCssDTO.getFecModificacion());
		tbSiaSelectoresCss.setScrapNoPattern(selectoresCssDTO.getScrapNoPattern());
		tbSiaSelectoresCss.setScrapPattern(selectoresCssDTO.getScrapPattern());
		tbSiaSelectoresCss.setSelImage(selectoresCssDTO.getSelImage());
		tbSiaSelectoresCss.setSelLinkProd(selectoresCssDTO.getSelLinkProd());
		tbSiaSelectoresCss.setSelPrecio(selectoresCssDTO.getSelPrecio());
		tbSiaSelectoresCss.setSelPreKilo(selectoresCssDTO.getSelPreKilo());
		tbSiaSelectoresCss.setSelProducto(selectoresCssDTO.getSelProducto());
		tbSiaSelectoresCss.setSelPaginacion(selectoresCssDTO.getSelPaginacion());	
		tbSiaSelectoresCss.setTbSiaEmpresa(new TbSiaEmpresa());
		tbSiaSelectoresCss.getTbSiaEmpresa().setDid(selectoresCssDTO.getDidEmpresa());
		tbSiaSelectoresCss.getTbSiaEmpresa().setNomEmpresa(selectoresCssDTO.getNomEmpresa());
		tbSiaSelectoresCss.setTbSiaUrl(new TbSiaUrl());
		tbSiaSelectoresCss.getTbSiaUrl().setDid(selectoresCssDTO.getDidUrl());
		tbSiaSelectoresCss.getTbSiaUrl().setNomUrl(selectoresCssDTO.getNomUrl());
		
		return tbSiaSelectoresCss;
	}
	
	/**
	 * Mapea una lista de de Entities a una lista de DTOs.
	 * 
	 * @param List<TbSiaSelectoresCss>
	 * @return List<SelectoresCssDTO>
	 */
	public List<SelectoresCssDTO> toListDTO(@NotNull final List<TbSiaSelectoresCss> lsTbSiaSelectoresCss) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<SelectoresCssDTO> listDto = Lists.newArrayList(); 
		
		lsTbSiaSelectoresCss.forEach(tbSiaSelectores -> {
			selectoresCssDTO = new SelectoresCssDTO();
			selectoresCssDTO.setDid(tbSiaSelectores.getDid());
			selectoresCssDTO.setBolActivo(tbSiaSelectores.getBolActivo());
			selectoresCssDTO.setFecModificacion(tbSiaSelectores.getFecModificacion());
			selectoresCssDTO.setScrapNoPattern(tbSiaSelectores.getScrapNoPattern());
			selectoresCssDTO.setScrapPattern(tbSiaSelectores.getScrapPattern());
			selectoresCssDTO.setSelImage(tbSiaSelectores.getSelImage());
			selectoresCssDTO.setSelLinkProd(tbSiaSelectores.getSelLinkProd());
			selectoresCssDTO.setSelPrecio(tbSiaSelectores.getSelPrecio());
			selectoresCssDTO.setSelPreKilo(tbSiaSelectores.getSelPreKilo());
			selectoresCssDTO.setSelProducto(tbSiaSelectores.getSelProducto());
			selectoresCssDTO.setSelPaginacion(tbSiaSelectores.getSelPaginacion());
			selectoresCssDTO.setDidEmpresa(tbSiaSelectores.getTbSiaEmpresa().getDid());
			selectoresCssDTO.setNomEmpresa(tbSiaSelectores.getTbSiaEmpresa().getNomEmpresa());		
			selectoresCssDTO.setDidUrl(tbSiaSelectores.getTbSiaUrl().getDid());
			selectoresCssDTO.setNomUrl(tbSiaSelectores.getTbSiaUrl().getNomUrl());
			listDto.add(selectoresCssDTO);			
		});

		return listDto;
	}
	
	@Override
	public List<SelectoresCssDTO> toListODTO(final List<Object[]> objeto) {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
