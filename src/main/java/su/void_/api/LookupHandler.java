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
import io.undertow.util.HeaderMap;
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

        HeaderMap requestHeaderMap = exchange.getRequestHeaders();
        String accept = requestHeaderMap.getFirst(Headers.ACCEPT);

        String output = "";
        if (accept.equals("text/plain; version=0.0.4")) {
            output = doPrometheus(serverCertificate, port);
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain; version=0.0.4");
        } else {
            output = doJson(serverCertificate);
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        }
        exchange.getResponseSender().send(output);
    }

    private String doJson(ServerCertificate serverCertificate) {
        Jsonb jsonb = JsonbBuilder.create();
        String output = jsonb.toJson(serverCertificate);
        return output;
    }

    private String doPrometheus(ServerCertificate serverCertificate, int port) {
        String notAfter = GaugeBuilder
                .create()
                .metric("not_after", String.valueOf(serverCertificate.getNotAfter()))
                .label("server_name", serverCertificate.getServerName() + ":" + port)
                .toString();
        String notBefore = GaugeBuilder
                .create()
                .metric("not_before", String.valueOf(serverCertificate.getNotBefore()))
                .label("server_name", serverCertificate.getServerName() + ":" + port)
                .toString();
        String remains = GaugeBuilder
                .create()
                .metric("remains", String.valueOf(serverCertificate.getRemains()))
                .label("server_name", serverCertificate.getServerName() + ":" + port)
                .toString();
        String validity = GaugeBuilder
                .create()
                .metric("validity", serverCertificate.getValidity() ? String.valueOf(1) : String.valueOf(0))
                .label("server_name", serverCertificate.getServerName() + ":" + port)
                .toString();
        String match = GaugeBuilder
                .create()
                .metric("match", serverCertificate.getMatch() ? String.valueOf(1) : String.valueOf(0))
                .label("server_name", serverCertificate.getServerName() + ":" + port)
                .toString();
        String output = notAfter + notBefore + remains + validity + match;
        return output;
    }
}
