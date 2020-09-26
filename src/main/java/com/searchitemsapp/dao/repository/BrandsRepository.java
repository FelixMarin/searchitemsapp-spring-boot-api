package com.searchitemsapp.dao.repository;

import java.io.IOException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dto.BrandsDto;
import com.searchitemsapp.entities.TbSiaMarcas;

@Component
public interface BrandsRepository  extends JpaRepository<TbSiaMarcas, Long>{
	BrandsDto findByDid(Integer did) throws IOException;
}
