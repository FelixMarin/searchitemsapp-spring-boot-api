package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.ProductNameDao;
import com.searchitemsapp.dao.repository.ProductNameRepository;
import com.searchitemsapp.dto.LiveSearchProductNameDto;
import com.searchitemsapp.entities.TbSiaNomProducto;

import lombok.AllArgsConstructor;

@SuppressWarnings("unchecked")
@Component
@AllArgsConstructor
public class ProductNameDaoImpl extends AbstractDao  implements ProductNameDao {
	
	private ProductNameRepository ifNomProductoRepository;
	private Environment env;

	@Override
	public LiveSearchProductNameDto findByDid(LiveSearchProductNameDto nomProducto) throws IOException {		
		return getModelMapper().map(ifNomProductoRepository
				.findById(nomProducto.getDid().longValue()), LiveSearchProductNameDto.class);
	}

	@Override
	public List<LiveSearchProductNameDto> findAll() throws IOException {
		
		List<LiveSearchProductNameDto> listDto = Lists.newArrayList();
				
				ifNomProductoRepository.findAll().forEach(elem -> listDto
						.add(getModelMapper().map(elem, LiveSearchProductNameDto.class)));
		
		return listDto;
	}
	
	public List<LiveSearchProductNameDto> findByNomProducto(String product) {

		List<LiveSearchProductNameDto> listDto = Lists.newArrayList(); 

		String nativeQuery = env.getProperty("flow.value.select.native.producto.by.nombre");
		
		Query q = getEntityManager().createNativeQuery(nativeQuery.replace("#", product), TbSiaNomProducto.class);
		
		List<TbSiaNomProducto> liEntities = (q.getResultList());
		
		liEntities.forEach(elem -> listDto
				.add(getModelMapper().map(elem, LiveSearchProductNameDto.class)));
		
		return listDto;
		
	}
}