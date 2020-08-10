package com.searchitemsapp.dao.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.entities.TbSiaSelectoresCss;

@Component
public interface SelectoresCssRepository  extends JpaRepository<TbSiaSelectoresCss, Long> {
	SelectoresCssDTO findByDid(Integer did) throws IOException;
	List<SelectoresCssDTO> findByTbSiaEmpresa(Integer did)  throws IOException;
}
