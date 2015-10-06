package com.chronosystems.wink.web.helper;

public class ColorHelper {

	public static void main(String args[]) {
		System.out.println("generated code is : " + gencode());
	}

	public static String gencode() {
		String[] letters = new String[15];
		letters = "0123456789ABCDEF".split("");
		String code = "#";
		for (int i = 0; i < 6; i++) {
			double ind = Math.random() * 15;
			int index = (int) Math.round(ind);
			code += letters[index];
		}
		return code;
	}
}