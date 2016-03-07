package com.fivetrue.utils;

public class TextUtils {
	
	public static boolean isEmpty(String text){
		boolean b = true;
		if(text != null && text.trim().length() > 0){
			b = false;
		}
		return b;
	}

}
