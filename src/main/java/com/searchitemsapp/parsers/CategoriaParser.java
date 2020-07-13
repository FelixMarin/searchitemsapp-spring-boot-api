package com.searchitemsapp.parsers;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.entities.TbSiaCategoriasEmpresa;
import com.searchitemsapp.entities.TbSiaEmpresa;
import com.searchitemsapp.entities.TbSiaMarcas;
import com.searchitemsapp.entities.TbSiaNomProducto;
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
public class CategoriaParser implements IFParser<CategoriaDTO, TbSiaCategoriasEmpresa>  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaParser.class);  
	
	@Autowired
	private CategoriaDTO categoriaPDto;
	
	@Autowired
	private TbSiaCategoriasEmpresa tbSiaPCategorias;
	
	public CategoriaParser() {
		super();
	}
	
	/**
	 * Mapea los datos de un objeto de tipo Entity a un objeto de tipo DTO.
	 * 
	 * @param TbSiaCategoriasEmpresa
	 * @return CategoriaDTO
	 */
	@Override
	public CategoriaDTO toDTO(@NotNull final TbSiaCategoriasEmpresa tbSiaPCategorias) {	
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		categoriaPDto.setBolActivo(tbSiaPCategorias.getBolActivo());
		categoriaPDto.setDesCatEmpresa(tbSiaPCategorias.getDesCatEmpresa());
		categoriaPDto.setDid(tbSiaPCategorias.getDid());
		categoriaPDto.setNomCatEmpresa(tbSiaPCategorias.getNomCatEmpresa());
		
		if(Objects.nonNull(tbSiaPCategorias.getTbSiaEmpresas()) &&
				!tbSiaPCategorias.getTbSiaEmpresas().isEmpty()) {
			TbSiaEmpresa tbSiaEmpresa = tbSiaPCategorias.getTbSiaEmpresas().get(NumberUtils.INTEGER_ZERO);
			Map<Integer, String> mapEmpresa = Maps.newHashMap();
			mapEmpresa.put(tbSiaEmpresa.getDid(), tbSiaEmpresa.getNomEmpresa());
			categoriaPDto.setEmpresas(mapEmpresa);
		}
		
		if(Objects.nonNull(tbSiaPCategorias.getTbSiaMarcas()) &&
				!tbSiaPCategorias.getTbSiaMarcas().isEmpty()) {
			TbSiaMarcas tbSiaMarcas = tbSiaPCategorias.getTbSiaMarcas().get(NumberUtils.INTEGER_ZERO);
			Map<Integer, String> mapMarcas = Maps.newHashMap();
			mapMarcas.put(tbSiaMarcas.getDid(), tbSiaMarcas.getNomMarca());
			categoriaPDto.setMarcas(mapMarcas);
		}
		
		if(Objects.nonNull(tbSiaPCategorias.getTbSiaNomProductos()) &&
				!tbSiaPCategorias.getTbSiaNomProductos().isEmpty()) {
			TbSiaNomProducto tbSiaNomProductos = tbSiaPCategorias.getTbSiaNomProductos().get(NumberUtils.INTEGER_ZERO);
			Map<Integer, String> mapProductos = Maps.newHashMap();
			mapProductos.put(tbSiaNomProductos.getDid(), tbSiaNomProductos.getNomProducto());
			categoriaPDto.setProductos(mapProductos);
		}
		return categoriaPDto;
	}
	
	/**
	 * Mapea los datos de un objeto de tipo DTO a un objeto de tipo Entity.
	 * 
	 * @param CategoriaDTO
	 * @return TbSiaCategoriasEmpresa
	 */
	@Override
	public TbSiaCategoriasEmpresa toTbSia(@NotNull final CategoriaDTO categoriaPDto) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		tbSiaPCategorias.setBolActivo(categoriaPDto.getBolActivo());
		tbSiaPCategorias.setDesCatEmpresa(categoriaPDto.getDesCatEmpresa());
		tbSiaPCategorias.setDid(categoriaPDto.getDid());
		tbSiaPCategorias.setNomCatEmpresa(categoriaPDto.getNomCatEmpresa());
		tbSiaPCategorias.setTbSiaEmpresas(Lists.newArrayList());
		tbSiaPCategorias.setTbSiaMarcas(Lists.newArrayList());
		tbSiaPCategorias.setTbSiaNomProductos(Lists.newArrayList());
		
		categoriaPDto.getEmpresas().entrySet().forEach(e -> {
			TbSiaEmpresa tbempresa = new TbSiaEmpresa();
			tbempresa.setDid(e.getKey());
			tbempresa.setNomEmpresa(e.getValue());
			tbSiaPCategorias.getTbSiaEmpresas().add(tbempresa);
		});
		
		categoriaPDto.getMarcas().entrySet().forEach(e -> {
			TbSiaMarcas tbmarcas = new TbSiaMarcas();
			tbmarcas.setDid(e.getKey());
			tbmarcas.setNomMarca(e.getValue());
			tbSiaPCategorias.getTbSiaMarcas().add(tbmarcas);
		});
		
		categoriaPDto.getProductos().entrySet().forEach(e -> {
			TbSiaNomProducto tbproductos = new TbSiaNomProducto();
			tbproductos.setDid(e.getKey());
			tbproductos.setNomProducto(e.getValue());
			tbSiaPCategorias.getTbSiaNomProductos().add(tbproductos);			
		});
		
		return tbSiaPCategorias;
	}
	
	/**
	 * Mapea una lista de de Entities a una lista de DTOs.
	 * 
	 * @param List<TbSiaCategoriasEmpresa>
	 * @return List<CategoriaDTO>
	 */
	@Override
	public List<CategoriaDTO> toListDTO(@NotNull final List<TbSiaCategoriasEmpresa> lsCategorias) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<CategoriaDTO> listDto = Lists.newArrayList(); 
		
		lsCategorias.forEach(e -> {
			categoriaPDto = new CategoriaDTO();
			categoriaPDto.setBolActivo(e.getBolActivo());
			categoriaPDto.setDesCatEmpresa(e.getDesCatEmpresa());
			categoriaPDto.setDid(e.getDid());
			categoriaPDto.setNomCatEmpresa(e.getNomCatEmpresa());
			
			if(Objects.nonNull(e.getTbSiaEmpresas()) &&
					!e.getTbSiaEmpresas().isEmpty()) {
				TbSiaEmpresa tbSiaEmpresa = e.getTbSiaEmpresas().get(NumberUtils.INTEGER_ZERO);
				Map<Integer, String> mapEmpresa = Maps.newHashMap();
				mapEmpresa.put(tbSiaEmpresa.getDid(), tbSiaEmpresa.getNomEmpresa());
				categoriaPDto.setEmpresas(mapEmpresa);
			}
			
			if(Objects.nonNull(e.getTbSiaMarcas()) &&
			!e.getTbSiaMarcas().isEmpty()) {
				TbSiaMarcas tbSiaMarcas = e.getTbSiaMarcas().get(NumberUtils.INTEGER_ZERO);
				Map<Integer, String> mapMarcas = Maps.newHashMap();
				mapMarcas.put(tbSiaMarcas.getDid(), tbSiaMarcas.getNomMarca());
				categoriaPDto.setMarcas(mapMarcas);
			}
			
			if(Objects.nonNull(e.getTbSiaNomProductos()) &&
					!e.getTbSiaNomProductos().isEmpty()) {
				TbSiaNomProducto tbSiaNomProductos = e.getTbSiaNomProductos().get(NumberUtils.INTEGER_ZERO);
				Map<Integer, String> mapProductos = Maps.newHashMap();
				mapProductos.put(tbSiaNomProductos.getDid(), tbSiaNomProductos.getNomProducto());
				categoriaPDto.setProductos(mapProductos);
			}
			
			listDto.add(categoriaPDto);			
		});
		
		return listDto;
	}

	@Override
	public List<CategoriaDTO> toListODTO(final List<Object[]> objeto) {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
