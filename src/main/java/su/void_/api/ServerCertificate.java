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

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ServerCertificate implements Serializable {
    private Long notAfter = null;
    private Long notBefore = null;
    private String distinguishedName = null;
    private Long remains = null;
    private Boolean validity = null;
    private Boolean match = false;
    private String serverName = null;

    @JsonbProperty(value = "not_after")
    public Long getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(Long notAfter) {
        this.notAfter = notAfter;
    }

    @JsonbProperty(value = "not_before")
    public Long getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Long notBefore) {
        this.notBefore = notBefore;
    }

    @JsonbProperty(value = "distinguished_name")
    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

    @JsonbProperty(value = "remains")
    public Long getRemains() {
        return remains;
    }

    public void setRemains(Long remains) {
        this.remains = remains;
    }

    @JsonbProperty(value = "validity")
    public Boolean getValidity() {
        return validity;
    }

    public void setValidity(Boolean validity) {
        this.validity = validity;
    }

    @JsonbProperty(value = "match")
    public Boolean getMatch() {
        return match;
    }

    public void setMatch(Boolean match) {
        this.match = match;
    }

    @JsonbProperty(value = "server_name")
    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
