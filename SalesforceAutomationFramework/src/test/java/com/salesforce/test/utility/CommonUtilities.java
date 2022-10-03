package com.salesforce.test.utility;

import java.io.*;
import java.util.*;

public class CommonUtilities {
	
	public static FileInputStream stream=null;
	
	public static Properties loadFile(String filename){
		Properties propertyFile = new Properties();
		String  PropertyFilePath=null;
		switch(filename) {
		case "data":
			PropertyFilePath =Constants.APPLICATION_PROPERTIES_PATH;
							break;
		case "studentRegistrationProperties":
			PropertyFilePath =Constants.STUDENT_REGISTRATION_PROPERTIES_PATH;
							break;
		}
		try {
			stream=new FileInputStream(PropertyFilePath);
			propertyFile.load(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propertyFile;
	}
	
	public String getApplicationProperty(String Key,Properties propertyFile) {
		String value = propertyFile.getProperty(Key);
		System.out.println("Property we got from the file is::" + value);
		try {
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public HashMap getAllPropertiesAsSet(Properties propertyFile) {
		
		return new HashMap(propertyFile);
	}
	
		public static String readPropertyData(String key) throws IOException
		{
			Properties f= loadFile("data");
			String val=f.getProperty(key);
			return val;
		}
	
}