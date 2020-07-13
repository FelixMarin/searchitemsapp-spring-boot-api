package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.searchitemsapp.dao.repository.IFCategoriaRepository;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.entities.TbSiaCategoriasEmpresa;
import com.searchitemsapp.parsers.IFParser;
import com.sun.istack.NotNull;

/**
 * Encapsula el acceso a la base de datos. Por lo que cuando la capa 
 * de lógica de negocio necesite interactuar con la base de datos, va 
 * a hacerlo a través de la API que le ofrece el DAO.
 * 
 * @author Felix Marin Ramirez
 *
 */
@SuppressWarnings({"unchecked"})
@Repository
public class CategoriaDao extends AbstractDao implements IFCategoriaRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaDao.class);   
	
	@Autowired
	private IFParser<CategoriaDTO, TbSiaCategoriasEmpresa> parser;
	
	@Autowired
	private Environment env;
	
	public CategoriaDao() {
		super();
	}
	
	/**
	 * Método que devuelve todos los elementos de la tabla {@link TbSiaCategoriasEmpresa}.
	 * 
	 * @return List<CategoriaDTO>
	 * @exception IOException
	 */
	@Override
	public List<CategoriaDTO> findAll() throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<CategoriaDTO> resultado = null;
		
		Query q = entityManager.createQuery(env
				.getProperty("flow.value.categoria.select.all"), TbSiaCategoriasEmpresa.class);
	
		try {
			resultado = parser.toListDTO(((List<TbSiaCategoriasEmpresa>) q.getResultList()));
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		return resultado;
	}
		
	/**
	 * A partir de un indentifcador se obtiene un elemento de la tabla.
	 * 
	 * @param id
	 * @return CategoriaDTO
	 */
	@Override
	public CategoriaDTO findByDid(@NotNull Integer did) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
			LOGGER.info(String.valueOf(did),this.getClass());
		}
				
		CategoriaDTO categoriaDTO = null;
		
		try {
			categoriaDTO = parser.toDTO(entityManager.find(TbSiaCategoriasEmpresa.class, did));
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		return categoriaDTO;
	}
}
