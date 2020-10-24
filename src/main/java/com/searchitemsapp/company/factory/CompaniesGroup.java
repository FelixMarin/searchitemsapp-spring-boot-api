package com.searchitemsapp.company.factory;

import com.searchitemsapp.company.Company;

public interface CompaniesGroup {

	abstract Company getInstance(Long companyId);
}
