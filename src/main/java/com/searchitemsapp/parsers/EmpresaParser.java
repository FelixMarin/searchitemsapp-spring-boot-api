package com.searchitemsapp.parsers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.entities.TbSiaCategoriasEmpresa;
import com.searchitemsapp.entities.TbSiaEmpresa;
import com.searchitemsapp.entities.TbSiaPais;
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
public class EmpresaParser implements IFParser<EmpresaDTO, TbSiaEmpresa> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaParser.class); 
	
	private static final String FEC_MODIFICACION = "FEC_MODIFICACION";
	private static final String BOL_ACTIVO = "BOL_ACTIVO";
	private static final String SEL_PRODUCTO = "SEL_PRODUCTO";
	private static final String SEL_PRECIO_KILO = "SEL_PRECIO_KILO";
	private static final String SEL_PRECIO = "SEL_PRECIO";
	private static final String SEL_PAGINACION = "SEL_PAGINACION";
	private static final String SEL_LINK_PROD = "SEL_LINK_PROD";
	private static final String SEL_IMAGE = "SEL_IMAGE";
	private static final String SCRAP_NO_PATTERN = "SCRAP_NO_PATTERN";
	private static final String SCRAP_PATTERN = "SCRAP_PATTERN";
	private static final String DID = "DID";
	
	@Autowired
	private EmpresaDTO empresaPDto;
	
	@Autowired
	private TbSiaEmpresa tbSiaPEmpresas;
	
	public EmpresaParser() {
		super();
	}
	
	/**
	 * Mapea los datos de un objeto de tipo Entity a un objeto de tipo DTO.
	 * 
	 * @param TbSiaEmpresa
	 * @return EmpresaDTO
	 */
	@Override
	public EmpresaDTO toDTO(@NotNull final TbSiaEmpresa tbSiaPEmpresas) {	
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		empresaPDto.setBolActivo(tbSiaPEmpresas.getBolActivo());
		empresaPDto.setDesEmpresa(tbSiaPEmpresas.getDesEmpresa());
		empresaPDto.setDid(tbSiaPEmpresas.getDid());
		empresaPDto.setNomEmpresa(tbSiaPEmpresas.getNomEmpresa());
		empresaPDto.setBolDynScrap(tbSiaPEmpresas.getBolDynScrap());
		
		empresaPDto.setDidPais(tbSiaPEmpresas.getTbSiaPais().getDid());
		empresaPDto.setNomPais(tbSiaPEmpresas.getTbSiaPais().getNomPais());
		empresaPDto.setDidCatEmpresa(tbSiaPEmpresas.getTbSiaCategoriasEmpresa().getDid());
		empresaPDto.setNomCatEmpresa(tbSiaPEmpresas.getTbSiaCategoriasEmpresa().getNomCatEmpresa());
		
		if(!tbSiaPEmpresas.getTbSiaSelectoresCsses().isEmpty()) {
			
			TbSiaSelectoresCss tbSiaSelectoresCsses = tbSiaPEmpresas
					.getTbSiaSelectoresCsses().get(NumberUtils.INTEGER_ZERO);
			
			Map<String, String> mapSelectores = Maps.newHashMap();
			mapSelectores.put(SCRAP_PATTERN, tbSiaSelectoresCsses.getScrapPattern());
			mapSelectores.put(SCRAP_NO_PATTERN, tbSiaSelectoresCsses.getScrapNoPattern());
			mapSelectores.put(SEL_IMAGE, tbSiaSelectoresCsses.getSelImage());
			mapSelectores.put(SEL_LINK_PROD, tbSiaSelectoresCsses.getSelLinkProd());
			mapSelectores.put(SEL_PAGINACION, tbSiaSelectoresCsses.getSelPaginacion());
			mapSelectores.put(SEL_PRECIO, tbSiaSelectoresCsses.getSelPrecio());
			mapSelectores.put(SEL_PRECIO_KILO, tbSiaSelectoresCsses.getSelPreKilo());
			mapSelectores.put(SEL_PRODUCTO, tbSiaSelectoresCsses.getSelProducto());
			mapSelectores.put(BOL_ACTIVO, tbSiaSelectoresCsses.getBolActivo().toString());
			mapSelectores.put(DID, tbSiaSelectoresCsses.getDid().toString());
			mapSelectores.put(FEC_MODIFICACION, tbSiaSelectoresCsses.getFecModificacion().toString());
			empresaPDto.setSelectores(mapSelectores);
		}
		
		if(!tbSiaPEmpresas.getTbSiaUrls().isEmpty()) {
			TbSiaUrl tbSiaUrl = tbSiaPEmpresas.getTbSiaUrls().get(NumberUtils.INTEGER_ZERO);
			Map<Integer, String> mapUrls = Maps.newHashMap();
			mapUrls.put(tbSiaUrl.getDid(), tbSiaUrl.getNomUrl());
			empresaPDto.setUrls(mapUrls);
		}
		
		return empresaPDto;
	}
	
	/**
	 * Mapea los datos de un objeto de tipo DTO a un objeto de tipo Entity.
	 * 
	 * @param EmpresaDTO
	 * @return TbSiaEmpresa
	 */
	@Override
	public TbSiaEmpresa toTbSia(@NotNull final EmpresaDTO empresaPDto) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		tbSiaPEmpresas.setBolActivo(empresaPDto.getBolActivo());
		tbSiaPEmpresas.setDesEmpresa(empresaPDto.getDesEmpresa());
		tbSiaPEmpresas.setDid(empresaPDto.getDid());
		tbSiaPEmpresas.setNomEmpresa(empresaPDto.getNomEmpresa());
		tbSiaPEmpresas.setBolDynScrap(empresaPDto.getBolDynScrap());
		tbSiaPEmpresas.setTbSiaUrls(Lists.newArrayList());
		tbSiaPEmpresas.setTbSiaSelectoresCsses(Lists.newArrayList());
		tbSiaPEmpresas.setTbSiaCategoriasEmpresa(new TbSiaCategoriasEmpresa());
		tbSiaPEmpresas.setTbSiaPais(new TbSiaPais());
		
		tbSiaPEmpresas.getTbSiaCategoriasEmpresa().setDid(empresaPDto.getDidCatEmpresa());
		tbSiaPEmpresas.getTbSiaCategoriasEmpresa().setNomCatEmpresa(empresaPDto.getNomCatEmpresa());
		tbSiaPEmpresas.getTbSiaPais().setDid(empresaPDto.getDidPais());
		tbSiaPEmpresas.getTbSiaPais().setNomPais(empresaPDto.getNomPais());
		
		empresaPDto.getUrls().entrySet().forEach(e -> {
			TbSiaUrl tburl = new TbSiaUrl();
			tburl.setDid(e.getKey());
			tburl.setNomUrl(e.getValue());
			tbSiaPEmpresas.getTbSiaUrls().add(tburl);			
		});
		
		Map<String,String> map = empresaPDto.getSelectores();
		TbSiaSelectoresCss tselectores = new TbSiaSelectoresCss();
		tselectores.setDid(Integer.parseInt(map.get(DID)));
		tselectores.setScrapPattern(map.get(SCRAP_PATTERN));
		tselectores.setScrapNoPattern(map.get(SCRAP_NO_PATTERN));
		tselectores.setSelImage(map.get(SEL_IMAGE));
		tselectores.setSelLinkProd(map.get(SEL_LINK_PROD));
		tselectores.setSelPaginacion(map.get(SEL_PAGINACION));
		tselectores.setSelPrecio(map.get(SEL_PRECIO));
		tselectores.setSelPreKilo(map.get(SEL_PRECIO_KILO));
		tselectores.setSelProducto(map.get(SEL_PRODUCTO));
		tselectores.setBolActivo(Boolean.parseBoolean(map.get(BOL_ACTIVO)));
		tselectores.setFecModificacion(LocalDate.parse(map.get(FEC_MODIFICACION)));
		tbSiaPEmpresas.getTbSiaSelectoresCsses().add(tselectores);
		
		return tbSiaPEmpresas;
	}
	
	/**
	 * Mapea una lista de de Entities a una lista de DTOs.
	 * 
	 * @param List<TbSiaEmpresa>
	 * @return List<EmpresaDTO>
	 */
	@Override
	public List<EmpresaDTO> toListDTO(@NotNull final List<TbSiaEmpresa> lsEmpresas) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<EmpresaDTO> listDto = Lists.newArrayList(); 
		
		lsEmpresas.forEach(e -> {
			empresaPDto = new EmpresaDTO();
			empresaPDto.setBolActivo(e.getBolActivo());
			empresaPDto.setDesEmpresa(e.getDesEmpresa());
			empresaPDto.setDid(e.getDid());
			empresaPDto.setNomEmpresa(e.getNomEmpresa());
			empresaPDto.setBolDynScrap(e.getBolDynScrap());

			empresaPDto.setDidPais(e.getTbSiaPais().getDid());
			empresaPDto.setNomPais(e.getTbSiaPais().getNomPais());
			empresaPDto.setDidCatEmpresa(e.getTbSiaCategoriasEmpresa().getDid());
			empresaPDto.setNomCatEmpresa(e.getTbSiaCategoriasEmpresa().getNomCatEmpresa());
			
			if(!e.getTbSiaSelectoresCsses().isEmpty()) {
				
				TbSiaSelectoresCss tbSiaSelectoresCsses = e
						.getTbSiaSelectoresCsses().get(NumberUtils.INTEGER_ZERO);
				
				Map<String, String> mapSelectores = Maps.newHashMap();
				mapSelectores.put(SCRAP_PATTERN, tbSiaSelectoresCsses.getScrapPattern());
				mapSelectores.put(SCRAP_NO_PATTERN, tbSiaSelectoresCsses.getScrapNoPattern());
				mapSelectores.put(SEL_IMAGE, tbSiaSelectoresCsses.getSelImage());
				mapSelectores.put(SEL_LINK_PROD, tbSiaSelectoresCsses.getSelLinkProd());
				mapSelectores.put(SEL_PAGINACION, tbSiaSelectoresCsses.getSelPaginacion());
				mapSelectores.put(SEL_PRECIO, tbSiaSelectoresCsses.getSelPrecio());
				mapSelectores.put(SEL_PRECIO_KILO, tbSiaSelectoresCsses.getSelPreKilo());
				mapSelectores.put(SEL_PRODUCTO, tbSiaSelectoresCsses.getSelProducto());
				mapSelectores.put(BOL_ACTIVO, tbSiaSelectoresCsses.getBolActivo().toString());
				mapSelectores.put(DID, tbSiaSelectoresCsses.getDid().toString());
				mapSelectores.put(FEC_MODIFICACION, tbSiaSelectoresCsses.getFecModificacion().toString());
				empresaPDto.setSelectores(mapSelectores);
			}
			
			if(!e.getTbSiaUrls().isEmpty()) {
				TbSiaUrl tbSiaUrl = e.getTbSiaUrls().get(NumberUtils.INTEGER_ZERO);
				Map<Integer, String> mapUrls = Maps.newHashMap();
				mapUrls.put(tbSiaUrl.getDid(), tbSiaUrl.getNomUrl());
				empresaPDto.setUrls(mapUrls);
			}
			
			listDto.add(empresaPDto);			
		});
		
		return listDto;
	}
	
	@Override
	public List<EmpresaDTO> toListODTO(final List<Object[]> objeto) {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
