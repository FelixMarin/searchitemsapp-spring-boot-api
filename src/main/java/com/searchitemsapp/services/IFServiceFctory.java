package com.searchitemsapp.services;

public interface IFServiceFctory {

	abstract IFService<String,String> getService(final String nomService);
}
