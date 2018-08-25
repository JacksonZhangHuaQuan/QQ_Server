package com.lanqiao.qq.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	static Properties pro = new Properties();
	public static final String FILENAME = "server.properties";
	
	static{
		try {
			pro.load(Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream(FILENAME));
		} catch (IOException e) {
			System.out.println("��ȡ" + FILENAME + "�쳣!");
			e.printStackTrace();
		}
	}
	
	public static String readPro(String key){
		return pro.getProperty(key);
	}
}
