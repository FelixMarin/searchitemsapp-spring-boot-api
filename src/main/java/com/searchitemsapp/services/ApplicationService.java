package com.searchitemsapp.services;

import org.springframework.stereotype.Service;

import lombok.NonNull;


@FunctionalInterface
@Service
public interface ApplicationService {
	
	abstract String service(@NonNull final String strDidPais, @NonNull final String strDidCategoria,
			@NonNull final String strTipoOrdenacion, @NonNull final String strNomProducto,
			@NonNull final String strEmpresas);
}