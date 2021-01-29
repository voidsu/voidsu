/*
 * Copyright 2021 Dmitry Romanyuta
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package su.void_.api;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import co.elastic.apm.attach.ElasticApmAttacher;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ApplicationInitializer {
    private static final Logger LOG = Logger.getLogger(ApplicationInitializer.class);

    public void onStart(@Observes StartupEvent event) {
        LOG.info("The application is starting...");

        String elasticApmEnabled = System.getenv("ELASTIC_APM_ENABLED");
        if (elasticApmEnabled != null && elasticApmEnabled.equals("true")) {
            ElasticApmAttacher.attach();
        }
    }

    public void onStop(@Observes ShutdownEvent event) {
        LOG.info("The application is stopping...");
    }
}
