package com.searchitemsapp.business.enterprises.factory;

import com.searchitemsapp.business.enterprises.Company;

public interface CompaniesGroup {

	abstract Company getInstance(int companyId);
}
