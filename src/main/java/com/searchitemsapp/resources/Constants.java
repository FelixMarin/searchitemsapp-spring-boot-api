package com.searchitemsapp.resources;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Constants {
	
    TWO_DOTS(ValueConstants.TWO_DOTS),
    ERROR(ValueConstants.ERROR),
	NO_RESULT_TEXT(ValueConstants.NO_RESULT_TEXT),
	REGEX_SPECIAL_CHARACTERS(ValueConstants.REGEX_SPECIAL_CHARACTERS),
	REGEX_WORDS(ValueConstants.REGEX_WORDS),
	REGEX_EMPRESAS(ValueConstants.REGEX_EMPRESAS),
	REGEX_ORDENACION(ValueConstants.REGEX_ORDENACION),
	REGEX_ALL(ValueConstants.REGEX_ALL),	
	DECIMALS_EXTENSION(ValueConstants.DECIMALS_EXTENSION),
	DECIMAL_NUMBER_REGEX(ValueConstants.DECIMAL_NUMBER_REGEX),
	KILOGRAM_EXTENSION(ValueConstants.KILOGRAM_EXTENSION),
	DEFAULT_PRICE(ValueConstants.DEFAULT_PRICE),
	EURO_EXTENSION(ValueConstants.EURO_EXTENSION),
	NUMERIC_REGEX(ValueConstants.NUMERIC_REGEX),
	TREE_DOTS(ValueConstants.TREE_DOTS),
	ENIE_EROSKI(ValueConstants.ENIE_EROSKI),	
	ENIE_MIN(ValueConstants.ENIE_MIN),
	PIPE(ValueConstants.PIPE),
	DOT(ValueConstants.DOT),
	COMMA(ValueConstants.COMMA),
	ENIE_URL(ValueConstants.ENIE_URL),
	PAGE_REGEX(ValueConstants.PAGE_REGEX),
	ZERO(ValueConstants.ZERO),
	FROM_TO_ECI(ValueConstants.FROM_TO_ECI),
	USER_AGENT(ValueConstants.USER_AGENT),
	PROTOCOL_ACCESSOR(ValueConstants.PROTOCOL_ACCESSOR),
	ALL(ValueConstants.ALL);

    @Getter
    private final String value;

    private static class ValueConstants {
        
    	public static final String REGEX_SPECIAL_CHARACTERS = "[\\'|\\?|\\*|\\<|\\>|\\[|\\]|\\{|\\}|"
    			+ "\\.|\\/|\\\\|\\(|\\)|\\$|\\%|\\&|\\=|\\¿|\\@|\\\"|\\!|\\º|"
    			+ "\\ª|\\||\\#|\\_|\\+|\\-|\\·|\\¡||\\;|]";
    	
    	public static final String REGEX_WORDS = 
    			"\\b(\\w*java\\w*)|"
    			+ "((\\w*import\\w*))|"
    			+ "((\\w*input\\w*))|"
    			+ "((\\w*function\\w*))|"
    			+ "((\\w*echo\\w*))|"
    			+ "((\\w*select\\w*))|"
    			+ "((\\w*delete\\w*))|"
    			+ "((\\w*update\\w*))|"
    			+ "((\\w*alter\\w*))|"
    			+ "((\\w*drop\\w*))|"
    			+ "((\\w*null\\w*))|"
    			+ "((\\w*from\\w*))|"
    			+ "((\\w*where\\w*))|"
    			+ "((\\w*text\\w*))|"
    			+ "((\\w*eval\\w*))\\b";
    	
    	public static final String REGEX_EMPRESAS = 
    			"\\b(\\w*101\\w*)|(\\w*102\\w*)"
    			+ "|(\\w*103\\w*)|(\\w*104\\w*)"
    			+ "|(\\w*105\\w*)|(\\w*106\\w*)"
    			+ "|(\\w*107\\w*)|(\\w*108\\w*)"
    			+ "|(\\w*109\\w*)|(\\w*110\\w*)"
    			+ "|(\\w*111\\w*)|(\\w*112\\w*)"
    			+ "|(\\w*113\\w*)|(\\w*114\\w*)"
    			+ "|(\\w*115\\w*)|(\\w*,\\w*)\\b";
    	
    	private static final String REGEX_ORDENACION = "(\\b(1)|(2)\\b)";
    	private static final String REGEX_ALL = "(\\b(\\w*ALL\\w*)\\b)";
    	
        public static final String ERROR = "Error";
        public static final String TWO_DOTS = ":";
        public static final String NO_RESULT_TEXT = "No Results";
        
        private static final String COMMA = ",";
        private static final String ALL = "ALL";
        
    	private static final String DECIMALS_EXTENSION = ".00";
    	private static final String DECIMAL_NUMBER_REGEX = "(\\d*[,][0-9]*)|([0-9]{1,9})";
    	private static final String KILOGRAM_EXTENSION = "/kg";
    	private static final String DEFAULT_PRICE = "1000.00";	
    	private static final String PIPE = "|";
    	private static final String DOT = ".";	
    	private static final String EURO_EXTENSION = "/Eur";
    	
    	private static final String NUMERIC_REGEX = ".*… ([0-9]+)";
    	private static final String TREE_DOTS = "…";
    	
    	private static final String ENIE_MIN = "ñ";
    	private static final String ENIE_EROSKI = "$00f1";
    	private static final String ENIE_URL = "%F1";
    	
    	private static final String PAGE_REGEX = ".*&page=([0-9]+)";
    	private static final String FROM_TO_ECI = ".*de ([0-9]+)";
    	private static final String ZERO = "0";
    	
    	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36";
    	private static final String PROTOCOL_ACCESSOR ="://";
    }
}
