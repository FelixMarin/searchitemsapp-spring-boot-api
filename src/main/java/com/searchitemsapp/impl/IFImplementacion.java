package com.searchitemsapp.impl;

import java.io.IOException;
import java.util.List;

/**
 * Representa de forma abstracta el objeto que queremos crear, 
 * mediante esta interface se definen la estructura que tendrán
 * los objetos 'Impl' para la persistencia de entidades. 
 * 
 * @author Felix Marin Ramirez
 *
 * @param <R>
 * @param <T>
 */
public interface IFImplementacion<R,T> {
	
	public R findByDid(R r) throws IOException;
	public List<R> findAll() throws IOException;
	public List<R> findByTbSia(R r, T t) throws IOException;
	
}
