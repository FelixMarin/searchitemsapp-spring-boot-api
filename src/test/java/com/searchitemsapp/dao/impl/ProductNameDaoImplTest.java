package com.searchitemsapp.dao.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.searchitemsapp.dao.ProductNameDao;
import com.searchitemsapp.dto.LiveSearchDto;
import com.searchitemsapp.exception.ResourceNotFoundException;

@SpringBootTest
class ProductNameDaoImplTest {

	@Autowired
	private ProductNameDao dao;
	
	@Test
	void testFindByDid() throws IOException, ResourceNotFoundException {
		LiveSearchDto productName = LiveSearchDto.builder().did(101l).build();
		Optional<LiveSearchDto> optional = dao.findByDid(productName);
		assertTrue(optional.isPresent());
		assertEquals(101l, optional.get().getDid());
	}

	@Test
	void testFindAll() throws IOException {
		List<LiveSearchDto> liveSearchList = dao.findAll();
		assertNotNull(liveSearchList);
		assertFalse(liveSearchList.isEmpty());
		assertEquals(615, liveSearchList.size());
	}

	@Test
	void testFindByNomProducto() {
		List<LiveSearchDto> liveSearchList = dao.findByNomProducto("miel");
		assertNotNull(liveSearchList);
		assertFalse(liveSearchList.isEmpty());
	}
	
	@Test
	void testFindByDidException() {
	 
	    assertThrows(ResourceNotFoundException.class, () -> {
	    	LiveSearchDto productName = LiveSearchDto.builder().did(999l).build();
	    	dao.findByDid(productName);
	    });
	}

}
