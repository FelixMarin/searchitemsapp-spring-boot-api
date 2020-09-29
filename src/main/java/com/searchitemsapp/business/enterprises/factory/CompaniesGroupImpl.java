package com.searchitemsapp.business.enterprises.factory;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.searchitemsapp.business.enterprises.Company;
import com.searchitemsapp.business.enterprises.impl.AlcampoImpl;
import com.searchitemsapp.business.enterprises.impl.CapraboImpl;
import com.searchitemsapp.business.enterprises.impl.CarrefourImpl;
import com.searchitemsapp.business.enterprises.impl.CondisImpl;
import com.searchitemsapp.business.enterprises.impl.ConsumImpl;
import com.searchitemsapp.business.enterprises.impl.DiaImpl;
import com.searchitemsapp.business.enterprises.impl.ElCorteInglesImpl;
import com.searchitemsapp.business.enterprises.impl.EroskiImpl;
import com.searchitemsapp.business.enterprises.impl.HipercorImpl;
import com.searchitemsapp.business.enterprises.impl.LidlImpl;
import com.searchitemsapp.business.enterprises.impl.MercadonaImpl;
import com.searchitemsapp.business.enterprises.impl.SimplyImpl;
import com.searchitemsapp.business.enterprises.impl.UlaboxImpl;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CompaniesGroupImpl implements CompaniesGroup {
	
	
	private AlcampoImpl alcampo;
	private CapraboImpl caprabo;
	private CarrefourImpl carrefour;
	private CondisImpl condis;
	private ConsumImpl consum;
	private DiaImpl dia;
	private ElCorteInglesImpl elcorteingles;
	private EroskiImpl eroski;
	private HipercorImpl hipercor;
	private MercadonaImpl mercadona;
	private SimplyImpl simply;
	private UlaboxImpl ulabox;
	private LidlImpl lidl;	

	private Map<Integer, Company> setUp() {
		
		Map<Integer, Company> companies =  Maps.newHashMap();
 
		companies.put(alcampo.get_DID(),alcampo);
		companies.put(caprabo.get_DID(),caprabo);
		companies.put(carrefour.get_DID(), carrefour);
		companies.put(condis.get_DID(), condis);		
		companies.put(dia.get_DID(), dia);
		companies.put(elcorteingles.get_DID(), elcorteingles);
		companies.put(eroski.get_DID(), eroski);
		companies.put(hipercor.get_DID(), hipercor);
		companies.put(mercadona.get_DID(), mercadona);		
		companies.put(simply.get_DID(), simply);
		companies.put(ulabox.get_DID(), ulabox);
		companies.put(lidl.get_DID(), lidl);
		companies.put(consum.get_DID(), consum);
		
		return companies;
	}
	
	public Company getInstance(int companyId) {
		return this.setUp().get(companyId);
	}
}
