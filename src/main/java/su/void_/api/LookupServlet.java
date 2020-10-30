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

import io.undertow.util.Headers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LookupServlet extends HttpServlet {
    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        String address = request.getParameter("address");
        String portString = request.getParameter("port");
        int port = Integer.valueOf(portString);
        String serverName = request.getParameter("serverName");

        LookupService probeService = new LookupService();
        ServerCertificate serverCertificate = probeService.lookup(address, port, serverName);

        String acceptHeader = request.getHeader(Headers.ACCEPT_STRING);
        String output = "";
        AcceptHeaderParserPrometheus acceptHeaderParserPrometheus = new AcceptHeaderParserPrometheus(acceptHeader);
        if (acceptHeaderParserPrometheus.isPrometheus()) {
            output = doPrometheus(serverCertificate, port);
            response.setHeader(Headers.CONTENT_TYPE_STRING, "text/plain;version=0.0.4");
        } else {
            output = doJson(serverCertificate);
            response.setHeader(Headers.CONTENT_TYPE_STRING, "application/json");
        }

        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(output);
        writer.close();
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
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