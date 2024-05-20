package com.vitalika.todo.enumeration;

import java.util.Arrays;
import java.util.stream.Collectors;


public enum TodoStatusEnum {
	NEW("NEW","New"),
	RUNNING("RUNNING","Running"),
	SUCCESS("SUCCESS","Success"),
	CANCELLED("CANCELLED","Cancelled");

	private final String code;
	private final String name;
	
	TodoStatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static TodoStatusEnum lookup(String code) {
	    for (TodoStatusEnum item: TodoStatusEnum.values()) {
	    	if (item.code.equals(code)) {
	    		return item;
	    	}
	    }
 		return null;
	}
	
	public static String getAllAsString() {
		return Arrays.stream(TodoStatusEnum.values()).map(p -> p.getCode()).collect(Collectors.joining(","));
	}
	
	
}
