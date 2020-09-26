package com.searchitemsapp.dao.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.entities.TbSiaSelectoresCss;

@Component
public interface CssSelectorsRepository  extends JpaRepository<TbSiaSelectoresCss, Long> {
	CssSelectorsDto findByDid(Integer did) throws IOException;
	List<CssSelectorsDto> findByTbSiaEmpresa(Integer did)  throws IOException;
}
