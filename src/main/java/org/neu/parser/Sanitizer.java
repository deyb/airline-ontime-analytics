/**
 * 
 */
package org.neu.parser;

import org.apache.commons.lang.StringUtils;
import org.neu.model.SanitizedRecord;

/**
 * @author dey
 *
 */
public class Sanitizer {
	public static boolean isValid(String strRecord) {
		final CSVRecord record = new CSVRecord(strRecord);

		/*if (record.getFieldCount() != 110) {
			return false;
		}*/
		
		try {
			// Check for fields for the type
			if (StringUtils.isBlank(record.get(0))) {
				return false; // YEAR 0
			}
			
			if (StringUtils.isBlank(record.get(2))) {
				return false; // MONTH 2
			}
			
			if (StringUtils.isBlank(record.get(7))) {
				return false; // AIRLINE_ID 7
			}

			// Origin, Destination, CityName, State, StateName should not be empty
			if (StringUtils.isEmpty(record.get(14))) {
				return false; // ORIGIN 14
			}
			
			if (StringUtils.isEmpty(record.get(15))) {
				return false;// ORIGIN_CITY_NAME 15
			}
			
			if (StringUtils.isEmpty(record.get(18))) {
				return false;// ORIGIN_STATE_NM 18
			}
			
			if (StringUtils.isEmpty(record.get(16))) {
				return false;// ORIGIN_STATE_ABR 16
			}
			
			if (StringUtils.isEmpty(record.get(23))) {
				return false;// DEST 23
			}
			
			if (StringUtils.isEmpty(record.get(24))) {
				return false;// DEST_CITY_NAME 24
			}
			
			if (StringUtils.isEmpty(record.get(25))) {
				return false;// DEST_STATE_ABR 25
			}
			
			if (StringUtils.isEmpty(record.get(27))) {
				return false;// DEST_STATE_NM 27
			}

			int CRSArrTime = toMinutes(Integer.parseInt(record.get(40)));
			int CRSDepTime = toMinutes(Integer.parseInt(record.get(29)));
			int CRSElapsedTime = Integer.parseInt(record.get(50));
			
			if (!(CRSArrTime > 0 && CRSDepTime > 0 && CRSElapsedTime > 0)) {
				return false;
			}

			int timeZone = CRSArrTime - CRSDepTime - CRSElapsedTime;
			if (timeZone % 60 != 0) {
				return false;
			}

			// AirportID, AirportSeqID, CityMarketID, StateFips, Wac should be larger than 0
			if (!(Integer.parseInt(record.get(20)) > 0)) {
				return false;
			}
			
			if (!(Integer.parseInt(record.get(21)) > 0)) {
				return false;
			}
			
			if (!(Integer.parseInt(record.get(22)) > 0)) {
				return false;
			}
			
			if (!(Integer.parseInt(record.get(26)) > 0)) {
				return false;
			}
			
			if (!(Integer.parseInt(record.get(28)) > 0)) {
				return false;
			}

			// For cancelled Flights that following conditions must be checked
			int cancelled = Integer.parseInt(record.get(47));
			if (!(cancelled == 0 || cancelled == 1)) {
				return false;
			}

			double arrDelay = Double.parseDouble(record.get(42));
			double arrDelayMin = Double.parseDouble(record.get(43));
			double arrDel15 = Double.parseDouble(record.get(44));

			// if ArrDelay > 0 then ArrDelay should equal to ArrDelayMinutes
			if (cancelled == 0) {
				int arrTime = toMinutes(Integer.parseInt(record.get(41)));
				int depTime = toMinutes(Integer.parseInt(record.get(30)));
				int actualElapsedTime = Integer.parseInt(record.get(51));
				// ArrTime - DepTime - ActualElapsedTime - timeZone should be zero
				if ((arrTime - depTime - actualElapsedTime - timeZone) != 0) {
					return false;
				}

				//if ArrDelay > 0 then ArrDelay should equal to ArrDelayMinutes
				if (arrDelay > 0) {
					if (arrDelay != arrDelayMin) {
						return false;
					}
				}
			
				//if ArrDelay < 0 then ArrDelayMinutes should be zero
				if (arrDelay < 0) {
					if (arrDelayMin != 0) {
						return false;
					}
				}
				
				//if ArrDelayMinutes >= 15 then ArrDel15 should be true		
				if (arrDelayMin >= 15)
					if (arrDel15 != 1)
						return false;

			}
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public static SanitizedRecord getSanitizedRecord(String line) {
		final CSVRecord record = new CSVRecord(line);
		
		final String airportCode  = record.get(20);
		final String  airlineCode = record.get(7);
		final int year = Integer.parseInt(record.get(0));
		final int month = Integer.parseInt(record.get(2));
		
		
		final double delay;
		if (Integer.parseInt(record.get(47)) == 1) { //flight is cancelled
			delay = 4.0;
		} else { //not cancelled => arrDelayMin / CSRElapsedTime
			delay = Double.parseDouble(record.get(43)) / Integer.parseInt(record.get(50));
		}
		
		return new SanitizedRecord(airportCode, airlineCode, year, month, delay);
	}
	
	/**
	 * @return time in minutes since 0000 hours
	 */
	private static int toMinutes(int time) {
		return 60 * (time / 100) + (time % 100);
	}

}
