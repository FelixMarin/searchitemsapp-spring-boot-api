package com.searchitemsapp.repository;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.entities.TbSiaCategoriasEmpresa;

@Component
public interface CategoryRepository extends Repository<TbSiaCategoriasEmpresa, Long> {
}
