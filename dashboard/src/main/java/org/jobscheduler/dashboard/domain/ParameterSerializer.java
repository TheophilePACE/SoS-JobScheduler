package org.jobscheduler.dashboard.domain;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringEscapeUtils;
import org.jobscheduler.dashboard.util.SOSStreamUnzip;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ParameterSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String str, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeStartArray();
		if ((str != null) && !(str.equals("null")))
			jgen.writeString(StringEscapeUtils.escapeHtml(str));
		else
			jgen.writeString("No parameter");
		jgen.writeEndArray();

	}

}