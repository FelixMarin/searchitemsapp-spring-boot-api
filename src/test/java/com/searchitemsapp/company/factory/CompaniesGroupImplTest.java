package com.searchitemsapp.company.factory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CompaniesGroupImplTest {
	
	@Autowired
	private CompaniesGroup group;
	private Long empresas[]= {101l,103l,104l,105l,106l,107l,108l,109l,110l,111l,114l,116l};
	

	@Test
	void testCompaniesGroupImpl() {
		assertNotNull(group.getInstance(empresas[0]));
		assertNotNull(group.getInstance(empresas[1]));
		assertNotNull(group.getInstance(empresas[2]));
		assertNotNull(group.getInstance(empresas[3]));
		assertNotNull(group.getInstance(empresas[4]));
		assertNotNull(group.getInstance(empresas[5]));
		assertNotNull(group.getInstance(empresas[6]));
		assertNotNull(group.getInstance(empresas[7]));
		assertNotNull(group.getInstance(empresas[8]));
		assertNotNull(group.getInstance(empresas[9]));
		assertNotNull(group.getInstance(empresas[10]));
		assertNotNull(group.getInstance(empresas[11]));
	}
}
