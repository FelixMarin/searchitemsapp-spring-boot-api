package com.searchitemsapp.dao.repository;

import java.io.IOException;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dto.PaisDTO;
import com.searchitemsapp.entities.TbSiaPais;

/**
 * Interfaz que se encarga de gestionar todas las 
 * operaciones de persistencia contra la tabla 
 * 'TbSiaPais' de la base de datos.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public interface IFPaisRepository  extends Repository<TbSiaPais, Long> {
	PaisDTO findByDid(Integer did)  throws IOException;
}
