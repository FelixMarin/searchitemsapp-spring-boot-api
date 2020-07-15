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

import com.searchitemsapp.dao.repository.IFEmpresaRepository;
import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.entities.TbSiaEmpresa;
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
public class EmpresaDao extends AbstractDao implements IFEmpresaRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmpresaDao.class);     
		
	@Autowired
	private IFParser<EmpresaDTO, TbSiaEmpresa> parser;
	
	@Autowired
	private Environment env;
	
	public EmpresaDao() {
		super();
	}
	
	/**
	 * Método que devuelve todos los elementos de una tabla.
	 * 
	 * @return List<EmpresaDTO>
	 */
	@Override
	public List<EmpresaDTO> findAll() throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<EmpresaDTO> resultado = null;
		
		Query q = entityManager.createQuery(env
				.getProperty("flow.value.empresa.select.all"), TbSiaEmpresa.class);

		try {
			resultado = parser.toListDTO(((List<TbSiaEmpresa>) q.getResultList()));
		}catch(NoResultException e) {
			if(LOGGER.isInfoEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		return resultado;
	}
	
	/**
	 * A partir de un indentifcador se obtiene un elemento
	 * de la tabla.
	 * 
	 * @return EmpresaDTO
	 */
	@Override
	public EmpresaDTO findByDid(final Integer did) throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		EmpresaDTO empresaDto = null;
		
		try {
			empresaDto = parser.toDTO(entityManager.find(TbSiaEmpresa.class, did));
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		return empresaDto;
	}
	
	/**
	 * Devuelve una lista de empresas que pertenecen 
	 * a una categoria y tienen un código de empresa.
	 * 
	 * @param TbSiaCategoriasEmpresa
	 * @param Integer
	 * @return List<EmpresaDTO>
	 * @exception IOException
	 */
	@Override
	public List<EmpresaDTO> findByDidAndTbSiaCategoriasEmpresa(
			final Integer didEmpresa, 
			final Integer didCatEmpresa) throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<EmpresaDTO> listEmpresaDto = null;
	
		Query q = entityManager.createQuery(env
				.getProperty("flow.value.empresa.select.lista.empresas.by.empresa.y.categoria"));		
		
		q.setParameter(env.getProperty("flow.value.categoria.didEmpresa.key"), didEmpresa);	
		q.setParameter(env.getProperty("flow.value.categoria.didCategoriaEmpresa.key"), didCatEmpresa);	
		
		try {
			listEmpresaDto = parser.toListDTO(((List<TbSiaEmpresa>) q.getResultList()));
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		return listEmpresaDto;
	}
}
