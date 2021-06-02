package com.searchitemsapp.validators.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchitemsapp.validators.ListaProductosValidator;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ListaProductosValidatorImplTest {

	@Autowired
	private ListaProductosValidator validator;

	@Test
	void testIsParams() {

		String[] args = { "101", "101", "1", "miel", "103" };
		assertTrue(validator.isParams(args));

		String[] args2 = { "101", "101", "1", "miel", null };
		assertFalse(validator.isParams(args2));

		String[] args3 = { "101", "101", "1", null, "103" };
		assertFalse(validator.isParams(args3));

		assertThrows(IndexOutOfBoundsException.class, () -> {
			String[] args4 = { "101", "101", "1", "103" };
			validator.isParams(args4);
		});

		String[] args5 = { "101", "101", null, "miel", "103" };
		assertFalse(validator.isParams(args5));

		String[] args6 = { "", "", "", "", "", "", "" };
		assertFalse(validator.isParams(args6));

		String[] args7 = { "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "101", "1", "miel", "103" };
		assertFalse(validator.isParams(args7));

		String[] args8 = { "'", "101", "1", "miel", "103" };
		assertFalse(validator.isParams(args8));
		
		String[] args9 = { "101", "101", "3", "miel", "103" };
		assertFalse(validator.isParams(args9));

		String[] args10 = { "101", "101", "1", "miel", "ALL" };
		assertTrue(validator.isParams(args10));
		
		String[] args11 = { "101", "101", "1", "select", "101" };
		assertFalse(validator.isParams(args11));

		assertThrows(IndexOutOfBoundsException.class, () -> {
			String[] args12 = {};
			validator.isParams(args12);
		});
	}
	
	@Test
	void testIsParamsLiveSearch() {
		assertTrue(validator.isParamsLiveSearch("101"));
		assertTrue(validator.isParamsLiveSearch("test"));
		assertFalse(validator.isParamsLiveSearch(""));
		assertFalse(validator.isParamsLiveSearch("'"));
		assertFalse(validator.isParamsLiveSearch("."));
		assertFalse(validator.isParamsLiveSearch("select"));
		assertFalse(validator.isParamsLiveSearch("*"));
		assertFalse(validator.isParamsLiveSearch("from"));
	}

}
