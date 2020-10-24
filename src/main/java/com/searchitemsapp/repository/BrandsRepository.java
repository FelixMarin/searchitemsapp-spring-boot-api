package com.searchitemsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.entities.TbSiaMarcas;

@Component
public interface BrandsRepository  extends JpaRepository<TbSiaMarcas, Long>{
}
