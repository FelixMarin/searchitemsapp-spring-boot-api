package com.searchitemsapp.dao.repository;

import java.io.IOException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dto.PaisDTO;
import com.searchitemsapp.entities.TbSiaPais;

@Component
public interface PaisRepository  extends JpaRepository<TbSiaPais, Long> {
	PaisDTO findByDid(Integer did)  throws IOException;
}
