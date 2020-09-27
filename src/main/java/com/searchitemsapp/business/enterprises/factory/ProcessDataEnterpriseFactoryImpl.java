package com.searchitemsapp.business.enterprises.factory;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.searchitemsapp.business.enterprises.Enterprise;
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
public class ProcessDataEnterpriseFactoryImpl implements EnterpriseFactory {
	
	
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

	private Map<Integer, Enterprise> setUp() {
		
		Map<Integer, Enterprise> enterprises =  Maps.newHashMap();
 
		enterprises.put(alcampo.get_DID(),alcampo);
		enterprises.put(caprabo.get_DID(),caprabo);
		enterprises.put(carrefour.get_DID(), carrefour);
		enterprises.put(condis.get_DID(), condis);		
		enterprises.put(dia.get_DID(), dia);
		enterprises.put(elcorteingles.get_DID(), elcorteingles);
		enterprises.put(eroski.get_DID(), eroski);
		enterprises.put(hipercor.get_DID(), hipercor);
		enterprises.put(mercadona.get_DID(), mercadona);		
		enterprises.put(simply.get_DID(), simply);
		enterprises.put(ulabox.get_DID(), ulabox);
		enterprises.put(lidl.get_DID(), lidl);
		enterprises.put(consum.get_DID(), consum);
		
		return enterprises;
	}
	
	public Enterprise getInstance(int enterpriseId) {
		return this.setUp().get(enterpriseId);
	}
}
