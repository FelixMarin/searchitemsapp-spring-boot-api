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

import com.searchitemsapp.dao.repository.IFMarcasRepository;
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.entities.TbSiaMarcas;
import com.searchitemsapp.parsers.IFParser;


/**
 * Encapsula el acceso a la base de datos. Por lo que cuando la capa 
 * de lógica de negocio necesite interactuar con la base de datos, va 
 * a hacerlo a través de la API que le ofrece el DAO.
 * 
 * @author Felix Marin Ramirez
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class MarcasDao extends AbstractDao implements IFMarcasRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MarcasDao.class);     
	
	@Autowired
	private IFParser<MarcasDTO, TbSiaMarcas> parser;	
	
	@Autowired
	private Environment env;

	public MarcasDao() {
		super();
	}
	
	/**
	 * Método que devuelve todos los elementos de una tabla.
	 * 
	 * @return List<LoginDTO>
	 */
	@Override
	public List<MarcasDTO> findAll() throws IOException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<MarcasDTO> resultado = null;	
		
		Query q = entityManager.createQuery(env
				.getProperty("flow.value.marcas.select.all"), TbSiaMarcas.class);
		
		try {
			resultado = parser.toListDTO(((List<TbSiaMarcas>) q.getResultList()));
		}catch(NoResultException e) {			
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
			}
		}		
		
		return resultado;
	}

	/**
	 * A partir de un indentifcador se obtiene un elemento de la tabla.
	 * 
	 * @return MarcasDTO
	 */
	@Override
	public MarcasDTO findByDid(final Integer did) throws IOException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}	
		
		MarcasDTO resultado = null;

		try {
			resultado = parser.toDTO(entityManager.find(TbSiaMarcas.class, did));
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		return resultado;
	}	
}
