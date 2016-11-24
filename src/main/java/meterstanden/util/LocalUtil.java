package main.java.meterstanden.util;

import org.apache.log4j.Logger;

public class LocalUtil {
	
	private static Logger log = Logger.getLogger(LocalUtil.class);

	/**
	 * Run this method to update all maandverbruiken for all metersoorten.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		log.info("Going to update all meterverbruiken.");
		UpdateVerbruik.updateAll();
	}

}
