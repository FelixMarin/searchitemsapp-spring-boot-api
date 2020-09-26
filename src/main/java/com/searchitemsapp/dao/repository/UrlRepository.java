package com.searchitemsapp.dao.repository;
import java.io.IOException;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.searchitemsapp.dto.UrlDto;
import com.searchitemsapp.entities.TbSiaUrl;

@Component
public interface  UrlRepository  extends JpaRepository<TbSiaUrl, Long>{
	
	UrlDto findByDid(Integer did)  throws IOException;
	List<UrlDto> findByDidAndDesUrl(Integer didPais, String didCategoria) throws IOException;
	List<UrlDto> findByDidAndNomUrl(Integer didPais, String didCategoria) throws IOException;
}
