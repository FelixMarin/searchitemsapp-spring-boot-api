package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.NomProductoDao;
import com.searchitemsapp.dao.repository.NomProductoRepository;
import com.searchitemsapp.dto.NomProductoDTO;
import com.searchitemsapp.entities.TbSiaNomProducto;

import lombok.NoArgsConstructor;

@SuppressWarnings("unchecked")
@Component
@NoArgsConstructor
public class NomProductoDaoImpl extends AbstractDao  implements NomProductoDao {
	
	@Autowired
	NomProductoRepository ifNomProductoRepository;
	
	@Autowired
	private Environment env;

	@Override
	public NomProductoDTO findByDid(NomProductoDTO nomProducto) throws IOException {		
		return getModelMapper().map(ifNomProductoRepository
				.findById(nomProducto.getDid().longValue()), NomProductoDTO.class);
	}

	@Override
	public List<NomProductoDTO> findAll() throws IOException {
		
		List<NomProductoDTO> listDto = Lists.newArrayList();
				
				ifNomProductoRepository.findAll().forEach(elem -> listDto
						.add(getModelMapper().map(elem, NomProductoDTO.class)));
		
		return listDto;
	}
	
	public List<NomProductoDTO> findByNomProducto(String product) {

		List<NomProductoDTO> listDto = Lists.newArrayList(); 

		String nativeQuery = env.getProperty("flow.value.select.native.producto.by.nombre");
		
		Query q = getEntityManager().createNativeQuery(nativeQuery.replace("#", product), TbSiaNomProducto.class);
		
		List<TbSiaNomProducto> liEntities = (q.getResultList());
		
		liEntities.forEach(elem -> listDto
				.add(getModelMapper().map(elem, NomProductoDTO.class)));
		
		return listDto;
		
	}
}