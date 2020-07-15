package com.searchitemsapp.processdata;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.dto.PaisDTO;
import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.dto.UrlDTO;
import com.searchitemsapp.impl.IFUrlImpl;

/**
 * En esta clase se configuran las URLs de los
 * sitios web a los que posteriormente se extraerá
 * la información.
 *  
 * @author Felix Marin Ramirez
 *
 */
@Component
public class UrlComposer extends ProcessDataAbstract implements IFUrlComposer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UrlComposer.class);   

	private static final String EROSKI = "EROSKI";
	private static final String SIMPLY = "SIMPLY";
	private static final String CONDIS = "CONDIS";
	private static final String WILDCARD = "{1}";

	@Autowired
	private IFUrlImpl urlImpl;
	
	@Autowired
	private CategoriaDTO categoriaDto;
	
	@Autowired 
	private PaisDTO paisDto;

	public UrlComposer() {
		super();
	}
	
	/**
	 * Método que reemplaza los carateres comodines
	 * por el nombre del producto a buscar. Los comodines
	 * tienen la siguiente forma '{1}'.
	 * 
	 * @param didPais
	 * @param didCategoria
	 * @param producto
	 * @param empresas
	 * @param listTodosSelectoresCss
	 * @return List<UrlDTO>
	 * @throws IOException
	 */
	public List<UrlDTO> replaceWildcardCharacter(final String didPais, 
			final String didCategoria, 
			final String producto,
			final String empresas,
			final List<SelectoresCssDTO> listTodosSelectoresCss,
			final Map<String,EmpresaDTO> mapEmpresas) 
			throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		paisDto.setDid(NumberUtils.toInt(didPais));		
		categoriaDto.setDid(NumberUtils.toInt(didCategoria));
		
		List<UrlDTO> pListResultadoDto  = urlImpl.obtenerUrlsPorIdEmpresa(paisDto, categoriaDto, empresas);
		
		String productoTratadoAux = tratarProducto(producto);
		
		List<UrlDTO> listUrlDto = Lists.newArrayList();
	
		pListResultadoDto.forEach(urlDto -> {
			
			try {			
				
				cargaSelectoresCss(urlDto, listTodosSelectoresCss);
				
				String productoTratado = StringUtils.EMPTY;	
				if(urlDto.getBolActivo().booleanValue()) {
					if(mapEmpresas.get(EROSKI).getDid().equals(urlDto.getDidEmpresa())) {
						productoTratado = reemplazarCaracteresEroski(producto);
						productoTratado = tratarProducto(productoTratado);
					} else if(mapEmpresas.get(SIMPLY).getDid().equals(urlDto.getDidEmpresa())) {
						productoTratado = reeplazarCaracteresSimply(producto);
						productoTratado = tratarProducto(productoTratado);
					} else if(mapEmpresas.get(CONDIS).getDid().equals(urlDto.getDidEmpresa())) {
						productoTratado = reeplazarTildesCondis(producto);
						productoTratado = reeplazarCaracteresCondis(productoTratado);
						productoTratado = tratarProducto(productoTratado);
					} else {
						productoTratado = productoTratadoAux;
					}
					
					String urlAux = urlDto.getNomUrl();
					urlAux = urlAux.replace(WILDCARD, productoTratado);
					urlDto.setNomUrl(urlAux);
					listUrlDto.add(urlDto);
					
				} else {
					if(LOGGER.isInfoEnabled()) {
						LOGGER.info("La URL: ".
								concat(urlDto.getDid().toString()).
								concat(" esta deshabilitada."));
					}
				}
			}catch(IOException e) {
				throw new UncheckedIOException(e);
			}
		});
		
		return listUrlDto;
	}
	
	/**
	 * Este método obtinen los selectores correspondietes
	 * a cada una de las empresas solicitadas en la solicitud 
	 * de servicio.
	 * 
	 * @param resDtoUrls
	 * @param listTodosElementNodes
	 */
	private void cargaSelectoresCss(UrlDTO resDtoUrls, List<SelectoresCssDTO> listTodosElementNodes) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		if(Objects.nonNull(resDtoUrls) && 
				Objects.nonNull(resDtoUrls.getDidEmpresa()) &&
				Objects.nonNull(listTodosElementNodes) &&
				!listTodosElementNodes.isEmpty()) {
		
			Integer empDidEnUlrs = resDtoUrls.getDidEmpresa();
	
			SelectoresCssDTO selectoresCssDTO = null;			
			for (SelectoresCssDTO elementNodesDTO : listTodosElementNodes) {
				if (elementNodesDTO.getDidEmpresa().equals(empDidEnUlrs)) {
					selectoresCssDTO = elementNodesDTO;
					break;
				}
			}
			
			if(Objects.nonNull(selectoresCssDTO)) {	
				resDtoUrls.setSelectores(selectoresCssDTO);
			}
		}
	}
}
