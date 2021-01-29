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

import org.jboss.logging.MDC;

import javax.ws.rs.Path;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/lookup")
public class LookupResource {
    @HeaderParam("X-Request-ID")
    private String requestId;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response lookupRest(LookupRequest lookupRequest) {
        MDC.put("requestId", requestId);

        LookupService probeService = new LookupService();
        ServerCertificate serverCertificate = probeService.lookup(
                lookupRequest.getAddress(), lookupRequest.getPort(), lookupRequest.getDomainName());
        return Response.ok(serverCertificate).build();
    }
}
