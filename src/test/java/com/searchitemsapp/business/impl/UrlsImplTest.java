package com.searchitemsapp.business.impl;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.searchitemsapp.business.Urls;
import com.searchitemsapp.dto.CssSelectorsDto;
import com.searchitemsapp.dto.SearchItemsParamsDto;
import com.searchitemsapp.dto.UrlDto;

@RunWith(SpringRunner.class)
@SpringBootTest
class UrlsImplTest {

	@Autowired
	private Urls urls;
	
	@Test
	void testReplaceUrlWildcard() throws IOException {
		List<CssSelectorsDto> listAllCssSelector = Lists.newArrayList();
		listAllCssSelector.add(CssSelectorsDto.builder().didEmpresa(105l).build());
		SearchItemsParamsDto searchedValue = SearchItemsParamsDto.builder().product("miel").sort("1")
				.pipedEnterprises("105").countryId("101").categoryId("101").build();
		List<UrlDto>res = urls.replaceUrlWildcard(searchedValue, listAllCssSelector);
		assertNotNull(res);
	}

}
