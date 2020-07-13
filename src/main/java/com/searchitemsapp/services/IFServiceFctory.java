package com.searchitemsapp.services;

public interface IFServiceFctory {

	abstract public IFService<String,String> getService(final String nomService);
}
