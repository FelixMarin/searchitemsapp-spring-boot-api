package com.searchitemsapp.dao.repository;
import java.io.IOException;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dto.EnterpriseDto;
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
public interface EnterpriseRepository extends JpaRepository<TbSiaEmpresa, Long>{
	TbSiaEmpresa findByDid(Integer did) throws IOException;
	List<EnterpriseDto> findByDidAndTbSiaCategoriasEmpresa(Integer didEmpresa, Integer didCatEmpresa) throws IOException;
}
