package com.searchitemsapp.dao.repository;

import java.io.IOException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dto.CountryDto;
import com.searchitemsapp.entities.TbSiaPais;

@Component
public interface CountryRepository  extends JpaRepository<TbSiaPais, Long> {
	CountryDto findByDid(Integer did)  throws IOException;
}
