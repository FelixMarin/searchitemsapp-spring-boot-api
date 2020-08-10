package com.searchitemsapp.dao.repository;

import java.io.IOException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.entities.TbSiaMarcas;

@Component
public interface MarcasRepository  extends JpaRepository<TbSiaMarcas, Long>{
	MarcasDTO findByDid(Integer did) throws IOException;
}
