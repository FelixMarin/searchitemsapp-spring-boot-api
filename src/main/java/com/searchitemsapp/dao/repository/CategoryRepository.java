package com.searchitemsapp.dao.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dto.CategoryDto;
import com.searchitemsapp.entities.TbSiaCategoriasEmpresa;
 
/**
 * Representa de forma abstracta el objeto que 
 * se encarga de gestionar todas las operaciones 
 * de persistencia contra la tabla 'TbSiaCategoriasEmpresa' 
 * de la base de datos.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public interface CategoryRepository extends Repository<TbSiaCategoriasEmpresa, Long> {
	List<CategoryDto> findAll() throws IOException;
	CategoryDto findByDid(Integer did);
}
