package org.jobscheduler.dashboard.domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class FieldSerializer extends JsonSerializer<Field> {

	@Override
	public void serialize(Field field, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeObjectField(field.name, field.value);
		jgen.writeEndObject();
	}
}
