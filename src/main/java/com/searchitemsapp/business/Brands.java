package com.searchitemsapp.business;

import java.io.IOException;
import java.util.Optional;

public interface Brands {

	abstract Optional<String> brandFilter(final Long companyId, final String productName)  throws IOException;
}
