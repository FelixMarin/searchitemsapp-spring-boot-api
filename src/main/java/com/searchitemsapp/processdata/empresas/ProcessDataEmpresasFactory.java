package com.searchitemsapp.processdata.empresas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ProcessDataEmpresasFactory implements IFProcessDataEmpresasFactory {
	
	@Autowired
	private ProcessDataAlcampo processdataAlcapo;
	
	@Autowired
	private ProcessDataCaprabo processdataCaprabo;
	
	@Autowired
	private ProcessDataCarrefour processdataCarrefour;
	
	@Autowired
	private ProcessDataCondis processdataCondis;
	
	@Autowired
	private ProcessDataConsum processdataConsum;
	
	@Autowired
	private ProcessDataDia processdataDia;
	
	@Autowired
	private ProcessDataECI processdataECI;
	
	@Autowired
	private ProcessDataEroski processdataEroski;
	
	@Autowired
	private ProcessDataHipercor processdataHipercor;
	
	@Autowired
	private ProcessDataMercadona processdataMercadona;
	
	@Autowired
	private ProcessDataSimply processdataSimply;
	
	@Autowired
	private ProcessDataUlabox processdataUlabox;
	
	@Autowired
	private ProcessDataLidl processdataLidl;	

	public IFProcessDataEmpresas getEnterpriseData(final int idEmpresa) {
		
		IFProcessDataEmpresas iFProcessDataEmpresas = null;
	
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
