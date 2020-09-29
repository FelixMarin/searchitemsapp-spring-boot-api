package com.searchitemsapp.business;

import java.io.IOException;

public interface Brands {

	abstract public String brandFilter(final int companyId, final String productName)  throws IOException;
}
