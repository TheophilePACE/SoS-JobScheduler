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

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A health indicator check for a component of your application
 */
public abstract class HealthCheckIndicator implements HealthIndicator<Map<String, HealthCheckIndicator.Result>> {

    private static final Result HEALTHY = new Result(true, null, null);

    /**
     * @return the name of the indicator
     */
    protected abstract String getHealthCheckIndicatorName();

    /**
     * Perform a check of the application component.
     *
     * @return if the component is healthy, a healthy Result; otherwise, an unhealthy Result
     *          with a descriptive error message or/and exception
     * @throws Exception if there is an unhandled error during the health check; this will result in
     *                   a failed health check
     */
    protected abstract Result check() throws Exception;

    @Override
    final public Map<String, Result> health() {
        Map<String, Result> results = new LinkedHashMap<>();
        try {
            results.put(getHealthCheckIndicatorName(), check());
        } catch (Exception e) {
            results.put(getHealthCheckIndicatorName(), unhealthy(e));
        }
        return results;
    }

    /**
     * @return a healthy Result with no additional message
     */
    public static Result healthy() {
        return HEALTHY;
    }

    /**
     * @param message an informative message
     * @return a healthy Result with an additional message
     */
    public static Result healthy(String message) {
        return new Result(true, message, null);
    }

    /**
     * @param message an informative message describing how the health check indicator failed
     * @return an unhealthy Result with the given message
     */
    public static Result unhealthy(String message) {
        return new Result(false, message, null);
    }

    /**
     * @param error an exception thrown during the health check
     * @return an unhealthy Result with the given error
     */
    public static Result unhealthy(Throwable error) {
        return new Result(false, error.getMessage(), error);
    }

    /**
     * @param message an informative message describing how the health check indicator failed
     * @param error an exception thrown during the health check
     * @return an unhealthy Result with the given error
     */
    public static Result unhealthy(String message, Throwable error) {
        return new Result(false, message, error);
    }

    /**
     * The result of a HealthCheckIndicator
     */
    public static class Result {
        private boolean healthy;
        private String message;
        private Throwable exception;

        private Result(boolean healthy) {
            this.healthy = healthy;
        }

        public Result(boolean healthy, String message) {
            this.healthy = healthy;
            this.message = message;
        }

        public Result(boolean healthy, String message, Throwable exception) {
            this.healthy = healthy;
            this.message = message;
            this.exception = exception;
        }

        public boolean isHealthy() {
            return healthy;
        }

        public String getMessage() {
            return message;
        }

        public String getException() {
            return ExceptionUtils.getFullStackTrace(exception);
        }
    }
}
