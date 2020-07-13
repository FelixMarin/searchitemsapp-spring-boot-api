package com.searchitemsapp.parsers;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.NomProductoDTO;
import com.searchitemsapp.entities.TbSiaCategoriasEmpresa;
import com.searchitemsapp.entities.TbSiaNomProducto;
import com.searchitemsapp.entities.TbSiaPais;
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
public class NomProductoParser implements IFParser<NomProductoDTO, TbSiaNomProducto> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NomProductoParser.class);  
	
	@Autowired
	private NomProductoDTO nomProductoDTO;
	
	@Autowired
	private TbSiaNomProducto tbSiaNomProducto;
	
	public NomProductoParser() {
		super();
	}

	/**
	 * Mapea los datos de un objeto de tipo Entity a un objeto de tipo DTO.
	 * 
	 * @param TbSiaNomProducto
	 * @return NomProductoDTO
	 */
	public NomProductoDTO toDTO(@NotNull final TbSiaNomProducto tbSiaNomProducto) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		nomProductoDTO.setDid(tbSiaNomProducto.getDid());
		nomProductoDTO.setNomProducto(tbSiaNomProducto.getNomProducto());	
		boolean isNull = null == tbSiaNomProducto.getTbSiaCategoriasEmpresa().getDid();
		nomProductoDTO.setDidCatEmpresas(isNull?101:tbSiaNomProducto.getTbSiaCategoriasEmpresa().getDid());
		nomProductoDTO.setNomCatEmpresas(tbSiaNomProducto.getTbSiaCategoriasEmpresa().getNomCatEmpresa());
		isNull = null == tbSiaNomProducto.getTbSiaCategoriasEmpresa().getDid();
		nomProductoDTO.setDidPais(isNull?101:tbSiaNomProducto.getTbSiaPais().getDid());
		nomProductoDTO.setNomPais(tbSiaNomProducto.getTbSiaPais().getNomPais());
		
		return nomProductoDTO;
	}
	
	/**
	 * Mapea los datos de un objeto de tipo DTO a un objeto de tipo Entity.
	 * 
	 * @param NomProductoDTO
	 * @return TbSiaNomProducto
	 */
	public TbSiaNomProducto toTbSia(@NotNull final NomProductoDTO nomProductoDTO) { 
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		tbSiaNomProducto.setDid(nomProductoDTO.getDid());
		tbSiaNomProducto.setNomProducto(nomProductoDTO.getNomProducto());
		tbSiaNomProducto.setTbSiaCategoriasEmpresa(new TbSiaCategoriasEmpresa());
		tbSiaNomProducto.setTbSiaPais(new TbSiaPais());		
		tbSiaNomProducto.getTbSiaCategoriasEmpresa().setDid(nomProductoDTO.getDid());
		tbSiaNomProducto.getTbSiaCategoriasEmpresa().setNomCatEmpresa(nomProductoDTO.getNomCatEmpresas());
		tbSiaNomProducto.getTbSiaPais().setDid(nomProductoDTO.getDid());
		tbSiaNomProducto.getTbSiaPais().setNomPais(nomProductoDTO.getNomPais());
		
		return tbSiaNomProducto;
	}
	
	/**
	 * Mapea una lista de de Entities a una lista de DTOs.
	 * 
	 * @param List<TbSiaNomProducto>
	 * @return List<NomProductoDTO>
	 */
	public List<NomProductoDTO> toListDTO(@NotNull final List<TbSiaNomProducto> lsTbSiaNomProducto) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<NomProductoDTO> listDto = Lists.newArrayList(); 
		
		lsTbSiaNomProducto.forEach(e -> {
			nomProductoDTO = new NomProductoDTO();
			nomProductoDTO.setDid(e.getDid());
			nomProductoDTO.setNomProducto(e.getNomProducto());
			boolean isNull = null == e.getTbSiaCategoriasEmpresa().getDid();
			nomProductoDTO.setDidCatEmpresas(isNull?101:e.getTbSiaCategoriasEmpresa().getDid());
			nomProductoDTO.setNomCatEmpresas(e.getTbSiaCategoriasEmpresa().getNomCatEmpresa());
			isNull = null == e.getTbSiaCategoriasEmpresa().getDid();
			nomProductoDTO.setDidPais(isNull?101:e.getTbSiaPais().getDid());
			nomProductoDTO.setNomPais(e.getTbSiaPais().getNomPais());
			listDto.add(nomProductoDTO);			
		});
		
		return listDto;
	}
	
	@Override
	public List<NomProductoDTO> toListODTO(List<Object[]> objeto) {
		throw new NotImplementedException("Funcionalidad no implementada");	
	}
}
