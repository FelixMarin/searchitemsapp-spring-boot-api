package com.searchitemsapp.business.enterprises.factory;

import org.springframework.stereotype.Component;

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

	public Enterprise getEnterpriseData(final int idEmpresa) {
		
		Enterprise enterprise = null;
	
		if(alcampo.get_DID() == idEmpresa) {
			enterprise = alcampo;
		} else if(caprabo.get_DID() == idEmpresa) {
			enterprise = caprabo;
		} else if(carrefour.get_DID() == idEmpresa) {
			enterprise = carrefour;
		} else if(condis.get_DID() == idEmpresa) {
			enterprise = condis;
		} else if(consum.get_DID() == idEmpresa) {
			enterprise = consum;
		} else if(dia.get_DID() == idEmpresa) {
			enterprise = dia;
		} else if(elcorteingles.get_DID() == idEmpresa) {
			enterprise = elcorteingles;
		} else if(eroski.get_DID() == idEmpresa) {
			enterprise = eroski;
		} else if(hipercor.get_DID() == idEmpresa) {
			enterprise = hipercor;
		} else if(mercadona.get_DID() == idEmpresa) {
			enterprise = mercadona;
		} else if(simply.get_DID() == idEmpresa) {
			enterprise = simply;
		} else if(ulabox.get_DID() == idEmpresa) {
			enterprise = ulabox;
		} else if(lidl.get_DID() == idEmpresa) {
			enterprise = lidl;
		}
		
		return enterprise;
	}
}
