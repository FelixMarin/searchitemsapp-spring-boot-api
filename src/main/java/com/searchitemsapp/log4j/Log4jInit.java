package com.searchitemsapp.log4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase encargada de configurar el módulo de trazas de log.
 * Este módulo se inicializa al arrancar el programa.
 *  
 * @author Felix Marin Ramirez
 *
 */
public class Log4jInit extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String LOG4J_APPENDER_FILE_FILE = "log4j.appender.file.File";
	private static final String LOG4J_INIT_FILE = "log4j-init-file";
	private static final String PROPERTIES_SIA = "PROPERTIES_SIA";	
	private static final String CURRENT_FILE_SEPARATOR = System.getProperty("file.separator");
	private static final Logger LOGGER = LoggerFactory.getLogger(Log4jInit.class);  

	public Log4jInit() {
		super();
	}

	/**
	 * Este método lee el fichero de propiedades de log4j
	 * y configura el control de trazas a partir de dicho
	 * fichero 'log4j.properties'.
	 * 
	 * @param ServletConfig
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		/**
		 * Se obtiene el valor de la variable de entorno
		 * 'PROPERTIES_SIA'. El valor de esta varible contiene
		 * la ruta hata el fichero de propiedades de log4j.
		 */
		String prefix = System.getenv(PROPERTIES_SIA);
		String file = config.getInitParameter(LOG4J_INIT_FILE);
		String filePath = prefix.concat(CURRENT_FILE_SEPARATOR).concat(file);
		Properties props = new Properties();
		
		/**
		 * En este bloque se lee el fichero de propiedades.
		 * y se añade a las propiedades de sesion de la aplicación.
		 * La configuración se carga al arrancar el programa.
		 */
		try {
			FileInputStream istream = new FileInputStream(filePath);
			props.load(istream);
			istream.close();
			String logFile = prefix + props.getProperty(LOG4J_APPENDER_FILE_FILE);
			props.setProperty(LOG4J_APPENDER_FILE_FILE, logFile);
			PropertyConfigurator.configure(props);
		} catch (IOException e) {
			if(LOGGER.isInfoEnabled()) {
				LOGGER.error(filePath,Log4jInit.class,e);
			}
		}
	}
}
