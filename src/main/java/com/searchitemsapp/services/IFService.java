package com.searchitemsapp.services;

import org.springframework.stereotype.Service;

/**
 * Representa de forma abstracta el objeto de tipo service. 
 * Mediante esta interface se definen la estructura que tendrán
 * dichos objetos. 
 * 
 * @author Felix Marin Ramirez
 *
 * @param <R>
 * @param <T>
 */
@SuppressWarnings("unchecked")
@FunctionalInterface
@Service
public interface IFService<R, T> {
	
	/*
	 * Métodos implementables
	 */
	public R service(final T... str);
}