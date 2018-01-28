package org.neu.model;

/**
 * 
 * @author dey
 *
 */
public class SanitizedRecord {
	public String airportCode;
	public String airlineCode;
	public int year;
	public int month;
	public double delay;

	public SanitizedRecord(String airportCode, String airlineCode, int year, int month, double delay) {
		this.airportCode = airportCode;
		this.airlineCode = airlineCode;
		this.year = year;
		this.month = month;
		this.delay = delay;
	}
}
