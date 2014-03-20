package org.jobscheduler.dashboard.domain;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringEscapeUtils;
import org.jobscheduler.dashboard.util.SOSStreamUnzip;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LogSerializer extends JsonSerializer<byte[]> {

	@Override
	public void serialize(byte[] bytes, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeStartArray();
		try {
			SOSStreamUnzip unzip = new SOSStreamUnzip(bytes);
			jgen.writeString(StringEscapeUtils.escapeHtml(unzip.unzip2String()));
		} catch (Exception e) {
			jgen.writeString("No log");
		}
		jgen.writeEndArray();

	}

}