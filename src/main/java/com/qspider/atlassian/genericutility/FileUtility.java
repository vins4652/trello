package com.qspider.atlassian.genericutility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * FileUtility can be used to read the common data from the properties file in the key value form
 * @author Vinaykumar Mannur
 *
 */

public class FileUtility {
	
	/**
	 * This method will get the common data from the properties file in string from
	 * @param key
	 * @return String
	 * @throws IOException
	 * @author Vinaykumar Mannur
	 */
	public String readCommondata(String key ) throws IOException {
		FileInputStream fis = new FileInputStream("./src/test/resources/trellocommondata.properties");
		Properties pobj = new Properties();
		pobj.load(fis);	
		String value = pobj.getProperty(key);
		return value;
	}
	
}
