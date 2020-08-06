package com.searchitemsapp.processdata;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.dto.SelectoresCssDTO;

public interface IFApplicationData {

	abstract void applicationData(final Map<String,EmpresaDTO> mapEmpresas, 
			final Map<Integer,Boolean> mapIsEmpresasDyn) throws IOException;
	
	abstract List<SelectoresCssDTO> listSelectoresCssPorEmpresa(
			final String strDidEmpresas);
	
	abstract List<MarcasDTO> getListTodasMarcas() throws IOException;
}
