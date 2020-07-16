package com.searchitemsapp.dao;

import java.io.IOException;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.searchitemsapp.dao.repository.IFPaisRepository;
import com.searchitemsapp.dto.PaisDTO;
import com.searchitemsapp.entities.TbSiaPais;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Repository
public class PaisDao extends AbstractDao implements IFPaisRepository {

	@Override
	public PaisDTO findByDid(final Integer did) throws IOException, NoResultException {
				
		return getModelMapper().map(getEntityManager()
				.find(TbSiaPais.class, did), PaisDTO.class);
	}
}
