package com.searchitemsapp.validators.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
		ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        MethodSignature signature = mock(MethodSignature.class);
        when(joinPoint.getSignature()).thenReturn(signature);
        
        String[] args = {"101","101","1","miel","103"};        
		validator.isParams(args, signature);
		
		assertThrows(NullPointerException.class, () -> {
			String[] args2 = {"101","101","1","miel",null};        
			validator.isParams(args2, signature);
		});
		
		assertThrows(NullPointerException.class, () -> {
			String[] args3 = {"101","101","1",null,"103"};        
			validator.isParams(args3, signature);
		});
		
        String[] args4 = {"101","101",null,"miel","103"};        
		validator.isParams(args4, signature);
	}

}
