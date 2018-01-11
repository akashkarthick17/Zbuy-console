package com.zilker.utility;

import java.util.Random;

public class GenerateOrderId {

	public static String getUniqueOrderId() {

		Random r = new Random();

		String str = "";

		for (int i = 0; i < 8; i++) {
			char c = (char) (r.nextInt(26) + 'a');
			str += "" + c;
		}

		return (str + System.currentTimeMillis());
	}
}
