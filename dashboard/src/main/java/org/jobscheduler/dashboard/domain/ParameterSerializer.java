/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
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