package com.searchitemsapp.processdata;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.PaisDTO;
import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.dto.UrlDTO;
import com.searchitemsapp.impl.IFUrlImpl;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class UrlComposer extends ProcessDataAbstract implements IFUrlComposer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UrlComposer.class);   

	@Autowired
	private IFUrlImpl ifUrlImpl;
	
	@Autowired
	private CategoriaDTO categoriaDto;
	
	@Autowired 
	private PaisDTO paisDto;

	public List<UrlDTO> replaceWildcardCharacter(final String strDidPais, 
			final String strDidCategoria, 
			final String strNomProducto,
			final String strEmpresas,
			final List<SelectoresCssDTO> listTodosSelectoresCss) 
			throws IOException {
		
		paisDto.setDid(NumberUtils.toInt(strDidPais));		
		categoriaDto.setDid(NumberUtils.toInt(strDidCategoria));
		
		List<UrlDTO> pListResultadoDto  = ifUrlImpl.obtenerUrlsPorIdEmpresa(paisDto, categoriaDto, strEmpresas);
		
		String productoTratadoAux = tratarProducto(strNomProducto);
		
		List<UrlDTO> listUrlDto = Lists.newArrayList();
	
		pListResultadoDto.forEach(urlDto -> {
			
			try {			
				
				cargaSelectoresCss(urlDto, listTodosSelectoresCss);
				
				String productoTratado = StringUtils.EMPTY;	
				
				if(urlDto.getBolActivo().booleanValue()) {
					if(getIFProcessDataEroski().get_DID() == urlDto.getDidEmpresa()) {
						productoTratado = getIFProcessDataEroski().reemplazarCaracteres(strNomProducto);
						productoTratado = tratarProducto(productoTratado);
					} else if(getIFProcessDataSimply().get_DID() == urlDto.getDidEmpresa()) {
						productoTratado = getIFProcessDataSimply().reemplazarCaracteres(strNomProducto);
						productoTratado = tratarProducto(productoTratado);
					} else if(getIFProcessDataCondis().get_DID() == urlDto.getDidEmpresa()) {
						productoTratado = getIFProcessDataCondis().eliminarTildesProducto(strNomProducto);
						productoTratado = getIFProcessDataCondis().reemplazarCaracteres(productoTratado);
						productoTratado = tratarProducto(productoTratado);
					} else {
						productoTratado = productoTratadoAux;
					}
					
					String urlAux = urlDto.getNomUrl();
					urlAux = urlAux.replace("{1}", productoTratado);
					urlDto.setNomUrl(urlAux);
					listUrlDto.add(urlDto);
					
				} else {
					if(LOGGER.isInfoEnabled()) {
						LOGGER.info("La URL: "
								.concat(urlDto.getDid().toString())
								.concat(" esta deshabilitada."));
					}
				}
			}catch(IOException e) {
				throw new UncheckedIOException(e);
			}
		});
		
		return listUrlDto;
	}

	private void cargaSelectoresCss(UrlDTO urlDTO, List<SelectoresCssDTO> listTodosElementNodes) {
	
		SelectoresCssDTO selectoresCssDTO = listTodosElementNodes
				.stream().filter(x -> x.getDidEmpresa().equals(urlDTO.getDidEmpresa()))
				.collect(Collectors.toList()).get(0);
			
		urlDTO.setSelectores(selectoresCssDTO);
	}
}