package com.searchitemsapp.dao.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dto.NomProductoDTO;
import com.searchitemsapp.entities.TbSiaNomProducto;

/**
 * Interfaz que se encarga de gestionar todas las 
 * operaciones de persistencia contra la tabla 
 * 'TbSiaNomProducto' de la base de datos.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public interface IFNomProductoRepository  extends Repository<TbSiaNomProducto, Long> {
	List<NomProductoDTO> findAll() throws IOException;
	NomProductoDTO findByDid(Integer did) throws IOException;
}
