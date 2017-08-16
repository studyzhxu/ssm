package com.zhxu.ssm.utils.authorization;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginDataUtils {

	private static Map<String, Object> loginData;

	private LoginDataUtils() {

	}

	public static Object getData(String key) {
		if (null == loginData) {
			loginData = new ConcurrentHashMap<>();
		}
		return loginData.get(key);
	}

	public static void setData(String key, Object obj) {
		if (null == loginData) {
			loginData = new ConcurrentHashMap<>();
		}
		loginData.put(key, obj);
	}

	public static void clearData(String key) {
		loginData.remove(key);
	}
}
