package com.searchitemsapp.dao.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.entities.TbSiaSelectoresCss;

/**
 * Interfaz que se encarga de gestionar todas las 
 * operaciones de persistencia contra la tabla 
 * 'TbSiaSelectoresCss' de la base de datos.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public interface IFSelectoresCssRepository  extends Repository<TbSiaSelectoresCss, Long> {
	List<SelectoresCssDTO> findAll() throws IOException;
	SelectoresCssDTO findByDid(Integer did) throws IOException;
	List<SelectoresCssDTO> findByTbSiaEmpresa(Integer did)  throws IOException;
}
