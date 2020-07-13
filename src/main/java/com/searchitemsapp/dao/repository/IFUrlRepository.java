package com.searchitemsapp.dao.repository;
import java.io.IOException;
import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dto.UrlDTO;
import com.searchitemsapp.entities.TbSiaUrl;

/**
 * Interfaz que se encarga de gestionar todas las 
 * operaciones de persistencia contra la tabla 
 * 'TbSiaUrl' de la base de datos.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public interface  IFUrlRepository  extends Repository<TbSiaUrl, Long>{
	
	List<UrlDTO> findAll() throws IOException;
	UrlDTO findByDid(Integer did)  throws IOException;
	List<UrlDTO> findByDidAndDesUrl(Integer didPais, String didCategoria) throws IOException;
	List<UrlDTO> findByDidAndNomUrl(Integer didPais, String didCategoria) throws IOException;
}
