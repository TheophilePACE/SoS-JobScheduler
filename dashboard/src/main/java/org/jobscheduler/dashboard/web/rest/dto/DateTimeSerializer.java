package org.jobscheduler.dashboard.web.rest.dto;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringEscapeUtils;
import org.jobscheduler.dashboard.util.SOSStreamUnzip;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateTimeSerializer extends JsonSerializer<DateTime> {

    private static DateTimeFormatter dateTimeFormatter = 
            DateTimeFormat.forPattern("yyyy-MM-dd HH:MM:ss");
    
	@Override
	public void serialize(DateTime dateTime, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeString(dateTimeFormatter.print(dateTime));

	}

}