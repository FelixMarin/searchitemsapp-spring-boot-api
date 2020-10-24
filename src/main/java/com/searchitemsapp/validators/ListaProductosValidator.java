package com.searchitemsapp.validators;

import org.aspectj.lang.reflect.MethodSignature;

public interface ListaProductosValidator {
	abstract void isParams(String[] args,  MethodSignature mSignature);
}
