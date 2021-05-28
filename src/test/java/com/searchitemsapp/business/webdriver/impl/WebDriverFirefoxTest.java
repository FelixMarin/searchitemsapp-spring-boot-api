package com.searchitemsapp.business.webdriver.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeEach;
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
class WebDriverFirefoxTest {

	 @Autowired
	 WebDriverFirefox wdFirefox;
	 
		@BeforeEach
		void setUpInicial() {
			wdFirefox.setUp();
		}
		
		@AfterClass
		void closeDriver() {
			wdFirefox.close();
		}
	
	@Test
	void firefoxExecutable() {
		assertNotNull(wdFirefox.firefoxExecutable());
	}

}
