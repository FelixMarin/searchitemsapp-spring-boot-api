package com.searchitemsapp.validators.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
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
		var joinPoint = mock(ProceedingJoinPoint.class);
		var signature = mock(MethodSignature.class);
		when(joinPoint.getSignature()).thenReturn(signature);

		String[] args = { "101", "101", "1", "miel", "103" };
		assertTrue(validator.isParams(args, signature));

		String[] args2 = { "101", "101", "1", "miel", null };
		assertFalse(validator.isParams(args2, signature));

		String[] args3 = { "101", "101", "1", null, "103" };
		assertFalse(validator.isParams(args3, signature));

		assertThrows(IndexOutOfBoundsException.class, () -> {
			String[] args4 = { "101", "101", "1", "103" };
			validator.isParams(args4, signature);
		});

		String[] args5 = { "101", "101", null, "miel", "103" };
		assertFalse(validator.isParams(args5, signature));

		String[] args6 = { "", "", "", "", "", "", "" };
		assertFalse(validator.isParams(args6, signature));

		assertThrows(IllegalArgumentException.class, () -> {
			String[] args7 = { "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "101", "1", "miel", "103" };
			validator.isParams(args7, signature);
		});

		String[] args8 = { "'", "101", "1", "miel", "103" };
		assertFalse(validator.isParams(args8, signature));

		String[] args9 = { "101", "101", "1", "miel", "ALL" };
		assertTrue(validator.isParams(args9, signature));

		assertThrows(IndexOutOfBoundsException.class, () -> {
			String[] args10 = {};
			validator.isParams(args10, signature);
		});
	}

}
