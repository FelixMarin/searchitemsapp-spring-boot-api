package com.searchitemsapp.processdata;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.dto.UrlDTO;

public interface IFProcessDataModule extends Callable<List<IFProcessPrice>> {

	abstract List<IFProcessPrice> call() throws IOException, URISyntaxException, InterruptedException;
	abstract void setListTodasMarcas(List<MarcasDTO> listTodasMarcas);
	abstract void setMapDynEmpresas(Map<Integer, Boolean> mapIsEmpresasDyn);
	abstract void setOrdenacion(String ordenacion);
	abstract void setProducto(String producto);
	abstract void setUrlDto(UrlDTO urlDto);
}
