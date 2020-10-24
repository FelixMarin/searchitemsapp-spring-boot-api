package com.searchitemsapp.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.entities.TbSiaUrl;

@Component
public interface  UrlRepository  extends JpaRepository<TbSiaUrl, Long>{
	
}
