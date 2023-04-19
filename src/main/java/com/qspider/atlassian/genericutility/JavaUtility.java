package com.qspider.atlassian.genericutility;

import java.time.LocalDateTime;
/**
 * JavaUtility can be used to get the data generated by the java library methods
 * @author Vinaykumar Mannur
 *
 */

public class JavaUtility {
	/**
	 * This method will give the time stamp of the Local date time from the current machine
	 * @return String
	 * @author Vinaykumar Mannur
	 */
	
	public String timeStamp() {
		
		String timeStamp = LocalDateTime.now().toString().replace(':', '-');
		return timeStamp;
	}

}
