package com.searchitemsapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase Abstracta que implementan todas las clases
 * DAO. En esta clase es donde se instancia al 
 * Entity Manager usado para interactuar con el 
 * contexto de persistencia. 
 * 
 * @author Felix Marin Ramirez
 *
 * @param <R>
 * @param <T>
 */
public abstract class AbstractDao{
	
	/*
	 * Variables Globales
	 */
	@PersistenceContext
	protected EntityManager entityManager;
	
	/*
	 * Constructor
	 */
	public AbstractDao() {
		super();
	}
}
