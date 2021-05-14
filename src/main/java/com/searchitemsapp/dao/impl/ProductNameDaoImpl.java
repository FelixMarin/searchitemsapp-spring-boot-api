package com.searchitemsapp.dao.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.ProductNameDao;
import com.searchitemsapp.dto.LiveSearchDto;
import com.searchitemsapp.entities.TbSiaNomProducto;
import com.searchitemsapp.exception.ResourceNotFoundException;
import com.searchitemsapp.repository.ProductNameRepository;

import lombok.AllArgsConstructor;

@SuppressWarnings("unchecked")
@Component
@AllArgsConstructor
public class ProductNameDaoImpl extends AbstractDao  implements ProductNameDao {
	
	private ProductNameRepository repository;
	private Environment environment;

	@Override
	public Optional<LiveSearchDto> findByDid(LiveSearchDto nomProducto) throws IOException, ResourceNotFoundException {
		Optional<TbSiaNomProducto> entity = repository.findById(nomProducto.getDid());
		return Optional.of(getModelMapper().map(entity.orElseThrow(() -> 
			new ResourceNotFoundException("Resource not found: " + 
					this.getClass().getName())), LiveSearchDto.class));
	}

	@Override
	public List<LiveSearchDto> findAll() throws IOException {
		
		List<LiveSearchDto> listDto = Lists.newArrayList();
				
				repository.findAll().forEach(elem -> listDto
						.add(getModelMapper().map(elem, LiveSearchDto.class)));
		
		return listDto;
	}
	
	public List<LiveSearchDto> findByNomProducto(String product) {

		List<LiveSearchDto> liveSearchList = Lists.newArrayList(); 

		Optional<String> nativeQuery = Optional.ofNullable(environment.getProperty("flow.value.select.native.producto.by.nombre"));
		
		nativeQuery.ifPresent(predicate -> {
			predicate = predicate.replace("#", product);
			var q = getEntityManager().createNativeQuery(predicate, TbSiaNomProducto.class);
			List<TbSiaNomProducto> liEntities = (q.getResultList());
			
			liEntities.forEach(entity -> liveSearchList
					.add(getModelMapper().map(entity, LiveSearchDto.class)));
		});
		
		return liveSearchList;
		
	}
}