package com.searchitemsapp.resource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Constants {
	
    ERROR(ValueConstants.ERROR),
	REGEX_SPECIAL_CHARACTERS(ValueConstants.REGEX_SPECIAL_CHARACTERS),
	REGEX_WORDS(ValueConstants.REGEX_WORDS),
	REGEX_EMPRESAS(ValueConstants.REGEX_EMPRESAS),
	REGEX_DOLAR_PERCENT(ValueConstants.REGEX_DOLAR_PERCENT),
	REGEX_ORDENACION(ValueConstants.REGEX_ORDENACION),
	REGEX_ALL(ValueConstants.REGEX_ALL),	
	DECIMALS_EXTENSION(ValueConstants.DECIMALS_EXTENSION),
	COMMA_DECIMALS_EXTENSION(ValueConstants.COMMA_DECIMALS_EXTENSION),
	DECIMAL_NUMBER_REGEX(ValueConstants.DECIMAL_NUMBER_REGEX),
	KILOGRAM_EXTENSION(ValueConstants.KILOGRAM_EXTENSION),
	DEFAULT_PRICE(ValueConstants.DEFAULT_PRICE),
	EURO_EXTENSION(ValueConstants.EURO_EXTENSION),
	NUMERIC_REGEX(ValueConstants.NUMERIC_REGEX),
	TREE_DOTS(ValueConstants.TREE_DOTS),
	ENIE_EROSKI(ValueConstants.ENIE_EROSKI),	
	ENIE_U_HEX(ValueConstants.ENIE_U_HEX),
	ENIE_MIN(ValueConstants.ENIE_MIN),
	ENIE_MAY(ValueConstants.ENIE_MAY),
	PIPE(ValueConstants.PIPE),
	DOT(ValueConstants.DOT),
	SLASH(ValueConstants.SLASH),
	DOUBLE_SLASH(ValueConstants.DOUBLE_SLASH),
	COMMA(ValueConstants.COMMA),
	WILDCARD(ValueConstants.WILDCARD),
	ENIE_URL_F(ValueConstants.ENIE_URL_F),
	ENIE_URL_D(ValueConstants.ENIE_URL_D),
	PAGE_REGEX(ValueConstants.PAGE_REGEX),
	ZERO(ValueConstants.ZERO),
	ES_ES(ValueConstants.ES_ES),
	FROM_TO_ECI(ValueConstants.FROM_TO_ECI),
	USER_AGENT(ValueConstants.USER_AGENT),
	PROTOCOL_ACCESSOR(ValueConstants.PROTOCOL_ACCESSOR),
	ACCEPT_LANGUAGE(ValueConstants.ACCEPT_LANGUAGE),
	APPLICATION_JSON(ValueConstants.APPLICATION_JSON),
	TEXT_HTML_APPLICATION(ValueConstants.TEXT_HTML_APPLICATION),
	SPACE_URL(ValueConstants.SPACE_URL),
	EQUALS(ValueConstants.EQUALS),
	DOUBLE_ZERO(ValueConstants.DOUBLE_ZERO),
	ACCEPT(ValueConstants.ACCEPT),
	AMPERSAND(ValueConstants.AMPERSAND),
	GZIP_DEFLATE_SDCH(ValueConstants.GZIP_DEFLATE_SDCH),
	ACCEPT_ENCODING(ValueConstants.ACCEPT_ENCODING),
	
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
    	private static final String REGEX_DOLAR_PERCENT = "(\\%|\\$00)";
    	private static final String SLASH = "/";
    	private static final String DOUBLE_SLASH = "//";
    	
        public static final String ERROR = "Error";
        
        public static final String ACCEPT_LANGUAGE = "Accept-Language";
        public static final String ES_ES = "es-ES,es;q=0.8";
        
        private static final String DOUBLE_ZERO = "00";
        
        private static final String COMMA = ",";
        private static final String ALL = "ALL";
        private static final String SPACE_URL = "%20";
        private static final String EQUALS = "=";
        private static final String AMPERSAND = "&";
        
    	private static final String DECIMALS_EXTENSION = ".00";
    	private static final String COMMA_DECIMALS_EXTENSION = ",00";
    	private static final String DECIMAL_NUMBER_REGEX = "(\\d*[,][0-9]*)|([0-9]{1,9})";
    	private static final String KILOGRAM_EXTENSION = "/kg";
    	private static final String DEFAULT_PRICE = "1000.00";	
    	private static final String PIPE = "|";
    	private static final String DOT = ".";	
    	private static final String EURO_EXTENSION = "/Eur";
    	
    	private static final String WILDCARD = "{1}";
    	
    	private static final String NUMERIC_REGEX = ".*… ([0-9]+)";
    	private static final String TREE_DOTS = "…";
    	
    	private static final String ENIE_MIN = "ñ";
    	private static final String ENIE_MAY ="Ñ";
    	private static final String ENIE_EROSKI = "$00f1";
    	private static final String ENIE_U_HEX = "u00f1";
    	private static final String ENIE_URL_F = "%F1";
    	private static final String ENIE_URL_D = "%D1";
    	
    	private static final String PAGE_REGEX = ".*&page=([0-9]+)";
    	private static final String FROM_TO_ECI = ".*de ([0-9]+)";
    	private static final String ZERO = "0";
    	
    	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36";
    	private static final String PROTOCOL_ACCESSOR ="://";
    	
    	public static final String TEXT_HTML_APPLICATION = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    	public static final String ACCEPT = "Accept";
    	public static final String GZIP_DEFLATE_SDCH = "gzip, deflate, sdch";
    	public static final String ACCEPT_ENCODING = "Accept-Encoding";
    	
    	private static final String APPLICATION_JSON = "application/json";

    }
}
