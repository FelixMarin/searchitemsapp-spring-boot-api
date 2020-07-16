package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.searchitemsapp.dao.repository.IFCategoriaRepository;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.entities.TbSiaCategoriasEmpresa;

import lombok.NoArgsConstructor;

/**
 * Encapsula el acceso a la base de datos. Por lo que cuando la capa 
 * de lógica de negocio necesite interactuar con la base de datos, va 
 * a hacerlo a través de la API que le ofrece el DAO.
 * 
 * @author Felix Marin Ramirez
 *
 */
@SuppressWarnings("unchecked")
@NoArgsConstructor
@Repository
public class CategoriaDao extends AbstractDao implements IFCategoriaRepository {
	
	@Autowired
	private Environment env;

	@Override
	public List<CategoriaDTO> findAll() throws IOException, NoResultException {
		
		List<CategoriaDTO> liDto = Lists.newArrayList();
		
		Query q = getEntityManager().createQuery(env
				.getProperty("flow.value.categoria.select.all"), TbSiaCategoriasEmpresa.class);
		
		List<TbSiaCategoriasEmpresa> liEntities = ((List<TbSiaCategoriasEmpresa>) q.getResultList());
		
		liEntities.forEach(elem -> {
			liDto.add(getModelMapper().map(elem, CategoriaDTO.class));
		});
	
		return liDto;
	}

	@Override
	public CategoriaDTO findByDid(Integer did) throws NoResultException {
				
		return getModelMapper().map(getEntityManager()
					.find(TbSiaCategoriasEmpresa.class, did), CategoriaDTO.class);
	}
}
