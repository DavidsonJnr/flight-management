package com.flightreservation.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateUtil {
	
	private DateUtil() {}
	
	public static OffsetDateTime convertCETtoUTC(String timezone, LocalDateTime flightTime) {
        ZoneId zoneId = ZoneId.of(timezone);
        ZonedDateTime zonedDateTime = flightTime.atZone(zoneId);
        return zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime();
    }
	
}
