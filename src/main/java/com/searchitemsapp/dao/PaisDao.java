package com.searchitemsapp.dao;

import java.io.IOException;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.searchitemsapp.dao.repository.IFPaisRepository;
import com.searchitemsapp.dto.PaisDTO;
import com.searchitemsapp.entities.TbSiaPais;
import com.searchitemsapp.parsers.IFParser;



/**
 * Encapsula el acceso a la base de datos. Por lo que cuando la capa 
 * de lógica de negocio necesite interactuar con la base de datos, va 
 * a hacerlo a través de la API que le ofrece el DAO.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Repository
public class PaisDao extends AbstractDao implements IFPaisRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaisDao.class);     
	
	@Autowired
	private IFParser<PaisDTO, TbSiaPais> parser;

	public PaisDao() {
		super();
	}
		
	/**
	 * A partir de un indentifcador se obtiene un elemento
	 * de la tabla.
	 * 
	 * @return PaisDTO
	 */
	@Override
	public PaisDTO findByDid(final Integer did) throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
				
		PaisDTO paisDto = null;
		
		try {
			paisDto = parser.toDTO(entityManager.find(TbSiaPais.class, did));
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		return paisDto;
	}
}
