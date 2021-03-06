/*
 * Copyright (C) 2016 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.syndesis.model.metrics;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.syndesis.model.Kind;
import io.syndesis.model.WithId;

@Value.Immutable
@JsonDeserialize(builder = IntegrationMetricsSummary.Builder.class)
@SuppressWarnings("immutables")
public interface IntegrationMetricsSummary extends WithId<IntegrationMetricsSummary>, Serializable{

    @Override
    default Kind getKind() {
        return Kind.IntegrationMetricsSummary;
    }
    String getMetricsProvider();
    /**
     * @return Number of successful messages
     */
    Long getMessages();
    /**
     * @return Number of messages that resulted in error
     */
    Long getErrors();
    /**
     * @return most recent (re-) start Date of the integration, empty if no live pods
     * are found for this integration, which would mean that the integration is currently down.
     */
    Optional<Date> getStart();
    /**
     * @return the TimeStamp of when the last message for processed
     */
    Optional<Date> getLastProcessed();

    Optional<List<IntegrationDeploymentMetrics>> getIntegrationDeploymentMetrics();

    class Builder extends ImmutableIntegrationMetricsSummary.Builder {
        // allow access to ImmutablIntegrationMetricsSummary.Builder
    }
}
