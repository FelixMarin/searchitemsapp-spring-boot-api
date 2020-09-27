package com.searchitemsapp.controller;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.searchitemsapp.dto.LiveSearchDto;

class LiveSearchControllerTest {
	
	@Autowired
	LiveSearchController liveSearchController;

	@Test
	void testLiveSearchProduct() {
		ResponseEntity<List<LiveSearchDto>> product = liveSearchController.liveSearchProduct("LEC");
		assertNotNull(product);
	}

}
