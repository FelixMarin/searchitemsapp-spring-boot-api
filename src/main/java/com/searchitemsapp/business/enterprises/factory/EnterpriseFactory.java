package com.searchitemsapp.business.enterprises.factory;

import com.searchitemsapp.business.enterprises.Enterprise;

public interface EnterpriseFactory {

	abstract Enterprise getInstance(int enterpriseId);
}
