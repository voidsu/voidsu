/*
 * Copyright 2020 Dmitry Romanyuta
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

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

import javax.servlet.ServletException;

import static io.undertow.servlet.Servlets.servlet;

public class Application {
    private Application() {
    }

    public static void main(String[] args) {
        DeploymentInfo servletBuilder = Servlets.deployment()
                .setClassLoader(Application.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("voidsu.war")
                .addServlets(
                    servlet("LookupServlet", LookupServlet.class)
                        .addMapping("/lookup")
                );

        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();

        PathHandler pathHandler = null;
        try {
            pathHandler = Handlers.path()
                .addPrefixPath("/", manager.start());
        } catch (ServletException e) {
            e.printStackTrace();
        }
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "0.0.0.0")
                .setHandler(pathHandler)
                .build();
        server.start();
    }
}
