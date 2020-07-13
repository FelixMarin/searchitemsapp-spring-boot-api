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

import com.google.common.collect.Maps;
import com.searchitemsapp.dto.PaisDTO;
import com.searchitemsapp.entities.TbSiaEmpresa;
import com.searchitemsapp.entities.TbSiaMarcas;
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
public class PaisParser implements IFParser<PaisDTO, TbSiaPais> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaisParser.class); 
	
	@Autowired
	private PaisDTO paisPDto;
	
	@Autowired
	private TbSiaPais tbSiaPPais;
	
	public PaisParser() {
		super();
	}
	
	/**
	 * Mapea los datos de un objeto de tipo Entity a un objeto de tipo DTO.
	 * 
	 * @param TbSiaPais
	 * @return PaisDTO
	 */
	public PaisDTO toDTO(@NotNull final TbSiaPais tbSiaPPais) {	
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		paisPDto.setBolActivo(tbSiaPPais.getBolActivo());
		paisPDto.setDesPais(tbSiaPPais.getDesPais());
		paisPDto.setDid(tbSiaPPais.getDid());
		paisPDto.setNomPais(tbSiaPPais.getNomPais());
		
		if(Objects.nonNull(tbSiaPPais.getTbSiaEmpresas()) &&
				!tbSiaPPais.getTbSiaEmpresas().isEmpty()) {
			TbSiaEmpresa tbSiaEmpresa = tbSiaPPais.getTbSiaEmpresas().get(NumberUtils.INTEGER_ZERO);
			Map<Integer, String> mapEmpresa = Maps.newHashMap();
			mapEmpresa.put(tbSiaEmpresa.getDid(), tbSiaEmpresa.getNomEmpresa());
			paisPDto.setEmpresas(mapEmpresa);
		}
		
		if(Objects.nonNull(tbSiaPPais.getTbSiaMarcas()) &&
				!tbSiaPPais.getTbSiaMarcas().isEmpty()) {
			TbSiaMarcas tbSiaMarcas = tbSiaPPais.getTbSiaMarcas().get(NumberUtils.INTEGER_ZERO);
			Map<Integer, String> mapMarcas = Maps.newHashMap();
			mapMarcas.put(tbSiaMarcas.getDid(), tbSiaMarcas.getNomMarca());
			paisPDto.setMarcas(mapMarcas);
		}
		
		if(Objects.nonNull(tbSiaPPais.getTbSiaNomProductos()) &&
				!tbSiaPPais.getTbSiaNomProductos().isEmpty()) {
			TbSiaNomProducto tbSiaNomProductos = tbSiaPPais.getTbSiaNomProductos().get(NumberUtils.INTEGER_ZERO);
			Map<Integer, String> mapProductos = Maps.newHashMap();
			mapProductos.put(tbSiaNomProductos.getDid(), tbSiaNomProductos.getNomProducto());
			paisPDto.setProductos(mapProductos);
		}
		
		return paisPDto;
	}
	
	/**
	 * Mapea los datos de un objeto de tipo DTO a un objeto de tipo Entity.
	 * 
	 * @param PaisDTO
	 * @return TbSiaPais
	 */
	public TbSiaPais toTbSia(@NotNull final PaisDTO paisPDto) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		tbSiaPPais.setBolActivo(paisPDto.getBolActivo());
		tbSiaPPais.setDesPais(paisPDto.getDesPais());
		tbSiaPPais.setDid(paisPDto.getDid());
		tbSiaPPais.setNomPais(paisPDto.getNomPais());

		paisPDto.getEmpresas().entrySet().forEach(e -> {
			TbSiaEmpresa tbempresa = new TbSiaEmpresa();
			tbempresa.setDid(e.getKey());
			tbempresa.setNomEmpresa(e.getValue());
			tbSiaPPais.getTbSiaEmpresas().add(tbempresa);			
		});
		
		paisPDto.getMarcas().entrySet().forEach(e -> {
			TbSiaMarcas tbmarcas = new TbSiaMarcas();
			tbmarcas.setDid(e.getKey());
			tbmarcas.setNomMarca(e.getValue());
			tbSiaPPais.getTbSiaMarcas().add(tbmarcas);			
		});
		
		paisPDto.getProductos().entrySet().forEach(e -> {
			TbSiaNomProducto tbproductos = new TbSiaNomProducto();
			tbproductos.setDid(e.getKey());
			tbproductos.setNomProducto(e.getValue());
			tbSiaPPais.getTbSiaNomProductos().add(tbproductos);			
		});
		
		return tbSiaPPais;
	}

	@Override
	public List<PaisDTO> toListDTO(final List<TbSiaPais> objeto) {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
	
	@Override
	public List<PaisDTO> toListODTO(final List<Object[]> objeto) {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
