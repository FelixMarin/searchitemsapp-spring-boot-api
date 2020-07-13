package com.searchitemsapp.processdata.empresas;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * Clase Factory encargada de gestionar la creación de 
 * objetos de tipo service. Las peticiones a los services 
 * pasarán siempre por esta clase.
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public class ProcessDataEmpresasFactory implements IFProcessDataEmpresasFactory {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataEmpresasFactory.class);  
	
	private static final String ID_EROSKI = "107";
	private static final String ID_LIDL = "102";
	private static final String ID_HIPERCOR = "103";
	private static final String ID_ELCORTEINGLES = "111";
	private static final String ID_DIA = "105";
	private static final String ID_SIMPLY = "114";
	private static final String ID_CONDIS = "110";
	private static final String ID_ULABOX = "106";
	private static final String ID_CARREFOUR = "104";
	private static final String ID_ALCAMPO = "108";
	private static final String ID_CAPRABO = "109";
	private static final String ID_MERCADONA = "101";
	private static final String ID_CONSUM = "116";

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
	
	public ProcessDataEmpresasFactory() {
		super();
	}
	
	/**
	 * Método de la clase factory que gestiona la creación 
	 * de instancias de las clases de procesamiento de datos
	 * correspondientes a cada empresa.
	 * 
	 * @param idEmpresa
	 * @return IFScrapingEmpresas
	 */
	public IFProcessDataEmpresas getScrapingEmpresa(final int idEmpresa) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		IFProcessDataEmpresas ifs = null;
	
		if(NumberUtils.toInt(ID_ALCAMPO) == idEmpresa) {
			ifs = processdataAlcapo;
		} else if(NumberUtils.toInt(ID_CAPRABO) == idEmpresa) {
			ifs = processdataCaprabo;
		} else if(NumberUtils.toInt(ID_CARREFOUR) == idEmpresa) {
			ifs = processdataCarrefour;
		} else if(NumberUtils.toInt(ID_CONDIS) == idEmpresa) {
			ifs = processdataCondis;
		} else if(NumberUtils.toInt(ID_CONSUM) == idEmpresa) {
			ifs = processdataConsum;
		} else if(NumberUtils.toInt(ID_DIA) == idEmpresa) {
			ifs = processdataDia;
		} else if(NumberUtils.toInt(ID_ELCORTEINGLES) == idEmpresa) {
			ifs = processdataECI;
		} else if(NumberUtils.toInt(ID_EROSKI) == idEmpresa) {
			ifs = processdataEroski;
		} else if(NumberUtils.toInt(ID_HIPERCOR) == idEmpresa) {
			ifs = processdataHipercor;
		} else if(NumberUtils.toInt(ID_MERCADONA) == idEmpresa) {
			ifs = processdataMercadona;
		} else if(NumberUtils.toInt(ID_SIMPLY) == idEmpresa) {
			ifs = processdataSimply;
		} else if(NumberUtils.toInt(ID_ULABOX) == idEmpresa) {
			ifs = processdataUlabox;
		} else if(NumberUtils.toInt(ID_LIDL) == idEmpresa) {
			ifs = processdataLidl;
		}
		
		return ifs;
	}
}
