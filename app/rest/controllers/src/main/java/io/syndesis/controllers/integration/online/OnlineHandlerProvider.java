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
package io.syndesis.controllers.integration.online;

import java.util.Arrays;
import java.util.List;

import io.syndesis.controllers.ControllersConfigurationProperties;
import io.syndesis.controllers.StateChangeHandler;
import io.syndesis.controllers.StateChangeHandlerProvider;
import io.syndesis.dao.manager.DataManager;
import io.syndesis.integration.api.IntegrationProjectGenerator;
import io.syndesis.openshift.OpenShiftService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "controllers.integration.enabled", havingValue = "true", matchIfMissing = true)
public class OnlineHandlerProvider extends BaseHandler implements StateChangeHandlerProvider {

    private final DataManager dataManager;
    private final IntegrationProjectGenerator projectGenerator;
    private final ControllersConfigurationProperties properties;

    public OnlineHandlerProvider(
            DataManager dataManager,
            OpenShiftService openShiftService,
            IntegrationProjectGenerator projectGenerator,
            ControllersConfigurationProperties properties) {

        super(openShiftService);

        this.dataManager = dataManager;
        this.projectGenerator = projectGenerator;
        this.properties = properties;
    }

    @Override
    public List<StateChangeHandler> getStatusChangeHandlers() {
        return Arrays.asList(
            new PublishHandler(
                dataManager,
                openShiftService(),
                projectGenerator,
                properties
            ),
            new UnpublishHandler(openShiftService(), dataManager));
    }
}
