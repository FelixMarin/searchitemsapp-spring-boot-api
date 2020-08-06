package com.searchitemsapp.processdata;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.impl.IFImplementacion;

@Component
public class ApplicationData implements IFApplicationData {

	public ApplicationData() {
		super();
	}
	
	@Autowired
	private IFImplementacion<EmpresaDTO, CategoriaDTO> iFEmpresaImpl;
	
	@Autowired
	private IFImplementacion<MarcasDTO, CategoriaDTO> iFMarcasImpl;
	
	@Autowired
	private IFImplementacion<SelectoresCssDTO, EmpresaDTO> selectoresCssImpl;
	
	@Autowired
	private Environment env;

	public void applicationData(final Map<String,EmpresaDTO> mapEmpresas, 
			final Map<Integer,Boolean> mapDynEmpresas) throws IOException {
		
			List<EmpresaDTO> listEmpresaDto = iFEmpresaImpl.findAll();

			listEmpresaDto.stream().forEach(empresaDTO -> {
				mapEmpresas.put(empresaDTO.getNomEmpresa(), empresaDTO);
				mapDynEmpresas.put(empresaDTO.getDid(), empresaDTO.getBolDynScrap());
			});
		}
	
	public List<MarcasDTO> getListTodasMarcas() throws IOException  {
		return iFMarcasImpl.findAll();
	}
	
	public List<SelectoresCssDTO> listSelectoresCssPorEmpresa(
			final String didEmpresas) {

		String emp;
		
		if("ALL".equalsIgnoreCase(didEmpresas)) {
			emp = env.getProperty("flow.value.all.id.empresa");
		} else {
			emp = didEmpresas;
		}
		
		StringTokenizer st = new StringTokenizer(emp, ","); 			
		List<Integer> listaAux = Lists.newArrayList();
		
		while (st.hasMoreElements()) {
			listaAux.add(Integer.parseInt(String.valueOf(st.nextElement())));
			
		}
		
		List<SelectoresCssDTO> listaSelectoresResultado = Lists.newArrayList();
		
		listaAux.forEach(didEmpresa -> {
			
			try {
				 EmpresaDTO empresaDto = new EmpresaDTO();
				empresaDto.setDid(didEmpresa);			
				List<SelectoresCssDTO> lsel = selectoresCssImpl.findByTbSia(new SelectoresCssDTO(), empresaDto);
				listaSelectoresResultado.addAll(lsel);
			}catch(IOException e) {
				throw new UncheckedIOException(e);
			}
			
		});
		
		return listaSelectoresResultado;
	}
}
