package com.cssi.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropUtils {
	public final static Properties props = new Properties();
	static {
		 InputStream ins = null;  
			try {
				ins = new FileInputStream(System.getProperty("user.dir")+"/conf/config.properties"); 
				props.load(ins);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {  
				if (null != ins) {
					 try {
						ins.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}  
		    }
	}
	
	public static String getValue(String key) {
		return props.getProperty(key);
	}
	
}
