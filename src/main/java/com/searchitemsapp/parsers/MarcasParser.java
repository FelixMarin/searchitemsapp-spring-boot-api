package com.searchitemsapp.parsers;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.entities.TbSiaMarcas;
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
public class MarcasParser implements IFParser<MarcasDTO, TbSiaMarcas> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MarcasParser.class);  
	
	@Autowired
	private MarcasDTO marcasDto;
	
	@Autowired
	private TbSiaMarcas tbSiaMarcas;
	
	public MarcasParser() {
		super();
	}
	
	/**
	 * Mapea los datos de un objeto de tipo Entity a un objeto de tipo DTO.
	 * 
	 * @param TbSiaMarcas
	 * @return MaracasDTO
	 */
	public MarcasDTO toDTO(@NotNull final TbSiaMarcas tbSiaMarcas) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		marcasDto.setDid(tbSiaMarcas.getDid());
		marcasDto.setNomMarca(tbSiaMarcas.getNomMarca());
		boolean isNull = null == tbSiaMarcas.getTbSiaCategoriasEmpresa().getDid();
		marcasDto.setDidCatEmpresas(isNull?101:tbSiaMarcas.getTbSiaCategoriasEmpresa().getDid());
		marcasDto.setNomCatEmpresas(tbSiaMarcas.getTbSiaCategoriasEmpresa().getNomCatEmpresa());
		isNull = null == tbSiaMarcas.getTbSiaPais().getDid();
		marcasDto.setDidPais(isNull?101:tbSiaMarcas.getTbSiaPais().getDid());
		marcasDto.setNomPais(tbSiaMarcas.getTbSiaPais().getNomPais());
		
		return marcasDto;
	}
	
	/**
	 * Mapea los datos de un objeto de tipo DTO a un objeto de tipo Entity.
	 * 
	 * @param MaracasDTO
	 * @return TbSiaMarcas
	 */
	public TbSiaMarcas toTbSia(@NotNull final MarcasDTO marcasDto) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		tbSiaMarcas.setDid(marcasDto.getDid());
		tbSiaMarcas.setNomMarca(marcasDto.getNomMarca());
		
		return tbSiaMarcas;
	}
	
	/**
	 * Mapea una lista de de Entities a una lista de DTOs.
	 * 
	 * @param List<TbSiaMarcas>
	 * @return List<MaracasDTO>
	 */
	public List<MarcasDTO> toListDTO(@NotNull final List<TbSiaMarcas> lsTbSiaMarcas) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<MarcasDTO> listDto = Lists.newArrayList(); 
		
		lsTbSiaMarcas.forEach(e -> {
			marcasDto = new MarcasDTO();
			marcasDto.setDid(e.getDid());
			marcasDto.setNomMarca(e.getNomMarca());
			boolean isNull =  null == e.getTbSiaCategoriasEmpresa().getDid();
			marcasDto.setDidCatEmpresas(isNull?101:e.getTbSiaCategoriasEmpresa().getDid());
			marcasDto.setNomCatEmpresas(e.getTbSiaCategoriasEmpresa().getNomCatEmpresa());
			isNull = null == e.getTbSiaPais().getDid();
			marcasDto.setDidPais(isNull?101:e.getTbSiaPais().getDid());
			marcasDto.setNomPais(e.getTbSiaPais().getNomPais());
			
			listDto.add(marcasDto);			
		});
		
		return listDto;
	}

	@Override
	public List<MarcasDTO> toListODTO(final List<Object[]> objeto) {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
