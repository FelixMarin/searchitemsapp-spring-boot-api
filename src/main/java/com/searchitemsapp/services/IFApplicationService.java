package com.searchitemsapp.services;

import org.springframework.stereotype.Service;

import com.sun.istack.NotNull;

@FunctionalInterface
@Service
public interface IFApplicationService {
	
	abstract String service(@NotNull final String strDidPais, @NotNull final String strDidCategoria,
			@NotNull final String strTipoOrdenacion, @NotNull final String strNomProducto,
			@NotNull final String strEmpresas);
}