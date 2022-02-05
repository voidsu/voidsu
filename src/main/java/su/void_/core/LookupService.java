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

package su.void_.core;

import javax.naming.NotContextException;
import org.jboss.logging.Logger;
import su.void_.core.client.SocketService;

public class LookupService {
    private static final Logger LOG = Logger.getLogger(LookupService.class);

    public ServerCertificate lookup(String address, Integer port, String serverName) {
        LOG.infov(
            "request: [address: {0}, port: {1}, server_name: {2}]",
            address,
            port,
            serverName);
        ServerCertificate serverCertificate = null;
        try {
            SocketService socketService = new SocketService(address, port, serverName);
            socketService.create();
            serverCertificate = socketService.fetchServerCertificate();
            socketService.close();
        } catch (NotContextException e) {
            e.printStackTrace();
            serverCertificate = new ServerCertificate();
        }
        return serverCertificate;
    }
}
