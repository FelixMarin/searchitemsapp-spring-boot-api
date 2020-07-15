package com.searchitemsapp.parsers;

import java.util.List;

/**
 * 
 * @author Felix Marin Ramirez
 * 
 * Representa de forma abstracta el objeto que se va a crear, 
 * mediante esta interfaz se define la estructura que tendrá el 
 * objeto de tipo Parser.
 *
 * @param <R>
 * @param <T>
 */
public interface IFParser<R, T>  {
	R toDTO(final T objeto);
	List<R> toListDTO(final List<T> objeto);
	List<R> toListODTO(List<Object[]> objeto);
}