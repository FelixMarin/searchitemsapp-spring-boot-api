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
import com.searchitemsapp.dao.repository.IFUrlRepository;
import com.searchitemsapp.dto.UrlDTO;
import com.searchitemsapp.entities.TbSiaUrl;
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
public class UrlDao extends AbstractDao implements IFUrlRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlDao.class);  
	
	@Autowired
	private IFParser<UrlDTO, TbSiaUrl> parser;
	
	@Autowired
	private Environment env;

	public UrlDao() {
		super();
	}
	
	/**
	 * Método que devuelve todos los elementos de una tabla.
	 * 
	 * @return List<EmpresaDTO>
	 */
	@Override
	public List<UrlDTO> findAll() throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<UrlDTO> resultado = null;
		
		Query q = entityManager.createQuery(env
				.getProperty("flow.value.url.select.all"), TbSiaUrl.class);
		
		try {
			resultado = parser.toListDTO(((List<TbSiaUrl>) q.getResultList()));
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		return resultado;
	}
	
	/**
	 * Método que devuelve un elemento de la 
	 * tabla dependiendo del identificador
	 * 
	 * @return UrlDTO
	 */
	@Override
	public UrlDTO findByDid(final Integer did) throws IOException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		UrlDTO urlDto = null;
		
		try {
			urlDto = parser.toDTO(entityManager.find(TbSiaUrl.class, did));
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}

		return urlDto;
	}

	/**
	 * Devuelve una lista de URLs correspondientes
	 * a un pais y a una categoria.
	 * 
	 * @param didPais Integer
	 * @param didCategoria Integer
	 * @exception IOException
	 */
	@Override
	public List<UrlDTO> findByDidAndDesUrl(final Integer didPais, 
			final String didCategoria) throws IOException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<UrlDTO> listUrlDto = null;
		
		Query query = entityManager.createNativeQuery(env
				.getProperty("flow.value.url.select.url.by.pais.categoria"));	
		
		query.setParameter(env.getProperty("flow.value.empresa.didCategoria.key"), Integer.parseInt(didCategoria));
		query.setParameter(env.getProperty("flow.value.categoria.didPais.key"), didPais);

		try {			
			listUrlDto = parser.toListODTO((List<Object[]>) query.getResultList());
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}

		return listUrlDto;
	}

	/**
	 * Devuelve una lista de URLs correspondientes a un país y auna categoría.
	 * 
	 * @param Integer didPais
	 * @param Integer didCategoria
	 * @return List<UrlDTO>
	 * @exception IOException
	 */
	@Override
	public List<UrlDTO> findByDidAndNomUrl(final Integer didPais, final String didCategoria) throws IOException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<UrlDTO> listUrlDto = null;
		
		Query query = entityManager.createNativeQuery(env
				.getProperty("flow.value.url.select.url.by.bollogin"));
		
		query.setParameter(env.getProperty("flow.value.empresa.didCategoria.key"), Integer.parseInt(didCategoria));
		query.setParameter(env.getProperty("flow.value.categoria.didPais.key"), didPais);

		try {			
			listUrlDto = parser.toListODTO((List<Object[]>) query.getResultList());
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		return listUrlDto;
	}
}
