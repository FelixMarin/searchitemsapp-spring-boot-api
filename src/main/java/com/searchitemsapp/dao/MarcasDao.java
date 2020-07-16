package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.repository.IFMarcasRepository;
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.entities.TbSiaMarcas;

import lombok.NoArgsConstructor;

@SuppressWarnings("unchecked")
@NoArgsConstructor
@Repository
public class MarcasDao extends AbstractDao implements IFMarcasRepository {
	
	@Autowired
	private Environment env;

	@Override
	public List<MarcasDTO> findAll() throws IOException {
		
		List<MarcasDTO> listDto = Lists.newArrayList();
		
		Query q = getEntityManager().createQuery(env
				.getProperty("flow.value.marcas.select.all"), TbSiaMarcas.class);
		
		List<TbSiaMarcas> liEntities = (q.getResultList());
		
		liEntities.forEach(elem -> {
			listDto.add(getModelMapper().map(elem, MarcasDTO.class));
		});
		
		return listDto;
	}
	
	@Override
	public MarcasDTO findByDid(final Integer did) throws IOException {
		
		return getModelMapper().map((getEntityManager()
				.find(TbSiaMarcas.class, did)), MarcasDTO.class);
	}	
}
