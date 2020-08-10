package com.searchitemsapp.processdata.empresas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ProcessDataEmpresasFactoryImpl implements ProcessDataEmpresasFactory {
	
	@Autowired
	private ProcessDataAlcampoImpl processdataAlcapo;
	
	@Autowired
	private ProcessDataCapraboImpl processdataCaprabo;
	
	@Autowired
	private ProcessDataCarrefourImpl processdataCarrefour;
	
	@Autowired
	private ProcessDataCondisImpl processdataCondis;
	
	@Autowired
	private ProcessDataConsumImpl processdataConsum;
	
	@Autowired
	private ProcessDataDiaImpl processdataDia;
	
	@Autowired
	private ProcessDataEciImpl processdataECI;
	
	@Autowired
	private ProcessDataEroskiImpl processdataEroski;
	
	@Autowired
	private ProcessDataHipercorImpl processdataHipercor;
	
	@Autowired
	private ProcessDataMercadonaImpl processdataMercadona;
	
	@Autowired
	private ProcessDataSimplyImpl processdataSimply;
	
	@Autowired
	private ProcessDataUlabox processdataUlabox;
	
	@Autowired
	private ProcessDataLidlImpl processdataLidl;	

	public ProcessDataEmpresas getEnterpriseData(final int idEmpresa) {
		
		ProcessDataEmpresas iFProcessDataEmpresas = null;
	
		if(processdataAlcapo.get_DID() == idEmpresa) {
			iFProcessDataEmpresas = processdataAlcapo;
		} else if(processdataCaprabo.get_DID() == idEmpresa) {
			iFProcessDataEmpresas = processdataCaprabo;
		} else if(processdataCarrefour.get_DID() == idEmpresa) {
			iFProcessDataEmpresas = processdataCarrefour;
		} else if(processdataCondis.get_DID() == idEmpresa) {
			iFProcessDataEmpresas = processdataCondis;
		} else if(processdataConsum.get_DID() == idEmpresa) {
			iFProcessDataEmpresas = processdataConsum;
		} else if(processdataDia.get_DID() == idEmpresa) {
			iFProcessDataEmpresas = processdataDia;
		} else if(processdataECI.get_DID() == idEmpresa) {
			iFProcessDataEmpresas = processdataECI;
		} else if(processdataEroski.get_DID() == idEmpresa) {
			iFProcessDataEmpresas = processdataEroski;
		} else if(processdataHipercor.get_DID() == idEmpresa) {
			iFProcessDataEmpresas = processdataHipercor;
		} else if(processdataMercadona.get_DID() == idEmpresa) {
			iFProcessDataEmpresas = processdataMercadona;
		} else if(processdataSimply.get_DID() == idEmpresa) {
			iFProcessDataEmpresas = processdataSimply;
		} else if(processdataUlabox.get_DID() == idEmpresa) {
			iFProcessDataEmpresas = processdataUlabox;
		} else if(processdataLidl.get_DID() == idEmpresa) {
			iFProcessDataEmpresas = processdataLidl;
		}
		
		return iFProcessDataEmpresas;
	}
}
