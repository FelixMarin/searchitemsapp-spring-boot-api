package com.searchitemsapp.repository;
import java.io.IOException;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dto.CompanyDto;
import com.searchitemsapp.entities.TbSiaEmpresa;

@Component
public interface CompanyRepository extends JpaRepository<TbSiaEmpresa, Long>{
	List<CompanyDto> findByDidAndTbSiaCategoriasEmpresa(Integer didEmpresa, Integer didCatEmpresa) throws IOException;
}
