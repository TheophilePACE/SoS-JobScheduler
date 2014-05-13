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
package org.jobscheduler.dashboard.web.websocket.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.atmosphere.config.managed.Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ActivityDTOJacksonDecoder implements Decoder<String, ActivityDTO> {

    private static final Logger log = LoggerFactory.getLogger(ActivityDTOJacksonDecoder.class);

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public ActivityDTO decode(String jsonString) {
        try {
            return jsonMapper.readValue(jsonString, ActivityDTO.class);
        } catch (IOException e) {
            log.error("Error while decoding the String: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
