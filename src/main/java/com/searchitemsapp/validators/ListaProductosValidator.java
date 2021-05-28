package com.searchitemsapp.validators;

import org.aspectj.lang.reflect.MethodSignature;

public interface ListaProductosValidator {
	abstract boolean isParams(String[] args,  MethodSignature mSignature);
}
