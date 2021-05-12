package com.searchitemsapp.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderDtoTest {
	
	private static OrderDto order;

	@BeforeAll
	static void testBuilder() {
		order = OrderDto.builder().build();
	}

	@Test
	void testCategoria() {
		order.setCategoria("test");
		assertEquals("test",order.getCategoria());
	}

	@Test
	void testProducto() {
		order.setProducto("test");
		assertEquals("test",order.getProducto());
	}

}
