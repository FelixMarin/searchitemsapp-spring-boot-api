package com.searchitemsapp.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.entities.TbSiaNomProducto;

@Component
public interface ProductNameRepository  extends JpaRepository<TbSiaNomProducto, Long> {

}
