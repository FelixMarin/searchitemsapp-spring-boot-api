package com.searchitemsapp.dao;
import java.io.IOException;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.springframework.core.env.Environment;
import com.searchitemsapp.dao.repository.IFSelectoresCssRepository;
import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.entities.TbSiaSelectoresCss;
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
@SuppressWarnings("unchecked")
@Repository
public class SelectoresCssDao extends AbstractDao implements IFSelectoresCssRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SelectoresCssDao.class);  
	
	@Autowired
	private IFParser<SelectoresCssDTO, TbSiaSelectoresCss> parser;
	
	@Autowired
	private Environment env;
	
	public SelectoresCssDao() {
		super();
	}
		
	/**
	 * Método que devuelve todos los elementos de una tabla.
	 * 
	 * @return List<SelectoresCssDTO>
	 * @exception IOException
	 */
	@Override
	public List<SelectoresCssDTO> findAll() throws IOException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<SelectoresCssDTO> resultado = null;
			
		Query q = entityManager.createQuery(env
				.getProperty("flow.value.selectorescss.select.all"), TbSiaSelectoresCss.class);
	
		try {
			resultado = parser.toListDTO(((List<TbSiaSelectoresCss>) q.getResultList()));
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		return resultado;
	}

	/**
	 * A partir de un indentifcador se obtiene un elemento
	 * de la tabla.
	 * 
	 * @param Integer
	 * @return SelectoresCssDTO
	 * @exception IOException
	 */
	@Override
	public SelectoresCssDTO findByDid(@NotNull final Integer did) throws IOException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
				
		SelectoresCssDTO resultado = null;
		
		try {
			resultado = parser.toDTO(entityManager.find(TbSiaSelectoresCss.class, did));
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		return resultado;
	}

	/**
	 * Devuelve una lista de selectores correspondientes a una empresa.
	 * 
	 * @param TbSiaEmpresa
	 * @return List<SelectoresCssDTO>
	 * @exception IOException
	 */
	@Override
	public List<SelectoresCssDTO> findByTbSiaEmpresa(@NotNull final Integer didEmpresa) throws IOException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<TbSiaSelectoresCss> selectoresCssList = null;

		Query query = entityManager.createQuery(env
				.getProperty("flow.value.selectorescss.select.by.didEmpresa"), 
				TbSiaSelectoresCss.class);
		
		query.setParameter("didEmpresa", didEmpresa);

		try {
			selectoresCssList = query.getResultList();
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		return parser.toListDTO((selectoresCssList));
	}	
}
