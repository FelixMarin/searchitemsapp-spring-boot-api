package com.searchitemsapp.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.repository.IFUrlRepository;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.PaisDTO;
import com.searchitemsapp.dto.UrlDTO;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class UrlImpl implements IFUrlImpl, IFImplementacion<UrlDTO, CategoriaDTO> {
	
	private static final String COMA = ",";
	private static final String ALL = "ALL";

	@Autowired
	private IFUrlRepository urlDao;
	
	@Autowired
	private Environment env;

	@Override
	public UrlDTO findByDid(final UrlDTO urlDTO) throws IOException {
			
		return urlDao.findByDid(urlDTO.getDid());
	}
	
	public List<UrlDTO> obtenerUrls(PaisDTO paisDto, CategoriaDTO categoriaDto) throws IOException {
			
		return urlDao.findByDidAndDesUrl(paisDto.getDid(), String.valueOf(categoriaDto.getDid()));
	}
	
	public List<UrlDTO> obtenerUrlsLogin(PaisDTO paisDto, CategoriaDTO categoriaDto) throws IOException {
			
		return urlDao.findByDidAndNomUrl(paisDto.getDid(), String.valueOf(categoriaDto.getDid()));
	}
	
	public List<UrlDTO> obtenerUrlsPorIdEmpresa(final PaisDTO paisDto, 
			final CategoriaDTO categoriaDto,
			final String idsEmpresas) 
			throws IOException {
		
		String strIdsEmpresas;
		
		if(ALL.equalsIgnoreCase(idsEmpresas)) {
			strIdsEmpresas = env.getProperty("flow.value.all.id.empresa");
		} else {
			strIdsEmpresas = idsEmpresas;
		}
		
		String[] arIdsEpresas = tokenizeString(strIdsEmpresas, COMA);
		List<UrlDTO> lsIdsEmpresas = Lists.newArrayList();
				
		List<UrlDTO> listUrlDTO = urlDao.findByDidAndDesUrl(paisDto.getDid(), String.valueOf(categoriaDto.getDid()));
		
		if(Objects.isNull(listUrlDTO)) {
			return lsIdsEmpresas;
		}
		
		List<String> liIdsEmpresas = Arrays.asList(arIdsEpresas);
		
		liIdsEmpresas.forEach(strId -> {
			listUrlDTO.stream().filter(x -> x.getDidEmpresa() == Integer.parseInt(strId)).forEach(x -> {
				lsIdsEmpresas.add(x);
			});
		});
		
		return lsIdsEmpresas;
	}
	
	private String[] tokenizeString(final String cadena, final String token) {
		
		StringTokenizer st = new StringTokenizer(cadena, token); 		
		List<String> listaAux = Lists.newArrayList();
		
		while (st.hasMoreElements()) {
			listaAux.add((String) st.nextElement());
		}
		
		return listaAux.toArray(new String[0]);
	}

	@Override
	public List<UrlDTO> findAll() throws IOException {
		throw new NotImplementedException("Funcionalidad no implementada");
	}

	@Override
	public List<UrlDTO> findByTbSia(UrlDTO r, CategoriaDTO t) throws IOException {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
