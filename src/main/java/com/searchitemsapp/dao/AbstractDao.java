package com.searchitemsapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;

import lombok.Data;

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
@Data
public abstract class AbstractDao {
	
	private static final ModelMapper MODEL_MAPPER = new ModelMapper();
	
	@PersistenceContext
	private EntityManager entityManager;
	
	protected static ModelMapper getModelMapper() {
		return MODEL_MAPPER;
	}

}
