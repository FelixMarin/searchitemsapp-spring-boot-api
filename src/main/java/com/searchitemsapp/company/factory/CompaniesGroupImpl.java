package com.searchitemsapp.company.factory;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.searchitemsapp.company.Alcampo;
import com.searchitemsapp.company.Caprabo;
import com.searchitemsapp.company.Carrefour;
import com.searchitemsapp.company.Company;
import com.searchitemsapp.company.Condis;
import com.searchitemsapp.company.Consum;
import com.searchitemsapp.company.Dia;
import com.searchitemsapp.company.ElCorteIngles;
import com.searchitemsapp.company.Eroski;
import com.searchitemsapp.company.Hipercor;
import com.searchitemsapp.company.Lidl;
import com.searchitemsapp.company.Mercadona;
import com.searchitemsapp.company.Simply;
import com.searchitemsapp.company.Ulabox;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CompaniesGroupImpl implements CompaniesGroup {
	
	
	private Alcampo alcampo;
	private Caprabo caprabo;
	private Carrefour carrefour;
	private Condis condis;
	private Consum consum;
	private Dia dia;
	private ElCorteIngles elcorteingles;
	private Eroski eroski;
	private Hipercor hipercor;
	private Mercadona mercadona;
	private Simply simply;
	private Ulabox ulabox;
	private Lidl lidl;	

	private Map<Long, Company> setUp() {
		
		Map<Long, Company> companies =  Maps.newHashMap();
 
		companies.put(alcampo.getId(),alcampo);
		companies.put(caprabo.getId(),caprabo);
		companies.put(carrefour.getId(), carrefour);
		companies.put(condis.getId(), condis);		
		companies.put(dia.getId(), dia);
		companies.put(elcorteingles.getId(), elcorteingles);
		companies.put(eroski.getId(), eroski);
		companies.put(hipercor.getId(), hipercor);
		companies.put(mercadona.getId(), mercadona);		
		companies.put(simply.getId(), simply);
		companies.put(ulabox.getId(), ulabox);
		companies.put(lidl.getId(), lidl);
		companies.put(consum.getId(), consum);
		
		return companies;
	}
	
	public Company getInstance(Long companyId) {
		return this.setUp().get(companyId);
	}
}
