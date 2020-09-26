package com.searchitemsapp.validators;

import org.aspectj.lang.reflect.MethodSignature;

public interface ListaProductosValidator {
	abstract public void isParams(String[] args,  MethodSignature mSignature) throws IllegalArgumentException;
}
