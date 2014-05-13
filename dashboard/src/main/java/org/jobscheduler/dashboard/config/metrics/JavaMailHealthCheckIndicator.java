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
package org.jobscheduler.dashboard.config.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;

/**
 * SpringBoot Actuator HealthIndicator check for JavaMail.
 */
public class JavaMailHealthCheckIndicator extends HealthCheckIndicator {

    public static final String EMAIL_HEALTH_INDICATOR = "email";
	
    private final Logger log = LoggerFactory.getLogger(JavaMailHealthCheckIndicator.class);

    private JavaMailSenderImpl javaMailSender;

    public JavaMailHealthCheckIndicator() {
    }

    public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    protected String getHealthCheckIndicatorName() {
        return EMAIL_HEALTH_INDICATOR;
    }

    @Override
    protected Result check() throws Exception {
        log.debug("Initializing JavaMail health indicator");

        try {
            javaMailSender.getSession().getTransport().connect(javaMailSender.getHost(),
                    javaMailSender.getUsername(),
                    javaMailSender.getPassword());

            return healthy();

        } catch (MessagingException e) {
            log.debug("Cannot connect to e-mail server.", e);
            return unhealthy("Cannot connect to e-mail server", e);
        }
    }
}
