package com.searchitemsapp.dao.repository;
import java.io.IOException;
import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.entities.TbSiaEmpresa;

/**
 * Interfaz que se encarga de gestionar todas las 
 * operaciones de persistencia contra la tabla 
 * 'TbSiaEmpresa' de la base de datos.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public interface IFEmpresaRepository extends Repository<TbSiaEmpresa, Long>{
	List<EmpresaDTO> findAll() throws IOException;
	EmpresaDTO findByDid(Integer did) throws IOException;
	List<EmpresaDTO> findByDidAndTbSiaCategoriasEmpresa(Integer didEmpresa, Integer didCatEmpresa) throws IOException;
}
