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

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.Deque;
import java.util.Map;

public class LookupHandler implements HttpHandler {
    @Override
    public void handleRequest(final HttpServerExchange exchange) {
        Map<String, Deque<String>> parameters = exchange.getQueryParameters();
        Deque<String> addressDeque = parameters.get("address");
        String address = addressDeque.getLast();

        Deque<String> portDeque = parameters.get("port");
        String portString = portDeque.getLast();
        int port = Integer.valueOf(portString);

        Deque<String> serverNameDeque = parameters.get("serverName");
        String serverName = serverNameDeque.getLast();

        LookupService probeService = new LookupService();
        ServerCertificate serverCertificate = probeService.lookup(address, port, serverName);

        Jsonb jsonb = JsonbBuilder.create();
        String output = jsonb.toJson(serverCertificate);

        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "Content-Type: text/plain; charset=utf-8");
        exchange.getResponseSender().send(output);
    }
}
