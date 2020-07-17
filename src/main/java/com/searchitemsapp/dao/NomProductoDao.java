package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.repository.IFNomProductoRepository;
import com.searchitemsapp.dto.NomProductoDTO;
import com.searchitemsapp.entities.TbSiaNomProducto;

import lombok.NoArgsConstructor;

@SuppressWarnings("unchecked")
@NoArgsConstructor
@Repository
public class NomProductoDao extends AbstractDao implements IFNomProductoRepository {
	
	@Autowired
	private Environment env;

	@Override
	public List<NomProductoDTO> findAll() throws IOException {
		
		List<NomProductoDTO> listDto = Lists.newArrayList(); 
		
		Query q = getEntityManager().createQuery(env
				.getProperty("flow.value.nomproducto.select.all"), TbSiaNomProducto.class);
		
		List<TbSiaNomProducto> liEntities = (q.getResultList());
		
		liEntities.forEach(elem -> listDto
				.add(getModelMapper().map(elem, NomProductoDTO.class)));
		
		return listDto;
	}
	
	@Override
	public NomProductoDTO findByDid(Integer did) throws IOException {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
