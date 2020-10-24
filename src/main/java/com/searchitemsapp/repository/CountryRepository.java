package com.searchitemsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.entities.TbSiaPais;

@Component
public interface CountryRepository  extends JpaRepository<TbSiaPais, Long> {
}
