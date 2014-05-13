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
package org.jobscheduler.dashboard.web.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jobscheduler.dashboard.web.websocket.dto.ActivityDTO;
import org.jobscheduler.dashboard.web.websocket.dto.ActivityDTOJacksonDecoder;
import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.cpr.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Calendar;

@ManagedService(
        path = "/websocket/activity")
public class ActivityService {

    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);

    private Broadcaster b =
            BroadcasterFactory.getDefault().lookup("/websocket/tracker", true);

    private DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    private ObjectMapper jsonMapper = new ObjectMapper();

    @Disconnect
    public void onDisconnect(AtmosphereResourceEvent event) throws IOException {
        log.debug("Browser {} disconnected", event.getResource().uuid());
        AtmosphereRequest request = event.getResource().getRequest();
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setUuid(event.getResource().uuid());
        activityDTO.setPage("logout");
        String json = jsonMapper.writeValueAsString(activityDTO);
        for (AtmosphereResource trackerResource : b.getAtmosphereResources()) {
            trackerResource.getResponse().write(json);
        }
    }

    @Message(decoders = {ActivityDTOJacksonDecoder.class})
    public void onMessage(AtmosphereResource atmosphereResource, ActivityDTO activityDTO) throws IOException {
        AtmosphereRequest request = atmosphereResource.getRequest();
        activityDTO.setUuid(atmosphereResource.uuid());
        activityDTO.setIpAddress(request.getRemoteAddr());
        activityDTO.setTime(dateTimeFormatter.print(Calendar.getInstance().getTimeInMillis()));
        String json = jsonMapper.writeValueAsString(activityDTO);
        log.debug("Sending user tracking data {}", json);
        for (AtmosphereResource trackerResource : b.getAtmosphereResources()) {
            trackerResource.getResponse().write(json);
        }
    }
}
