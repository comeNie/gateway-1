package com.ehsy.transport.enums;

public enum SvcRespType {
	STRING, BYTES;

	public static SvcRespType getType(Class<?> clazz){
		if(clazz.getName().equals(Byte.class.getName())){
			return BYTES;
		}else{
			//default
			return STRING;
		}
	}
}
