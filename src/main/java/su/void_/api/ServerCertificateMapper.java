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

import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.Instant;

public class ServerCertificateMapper {
    private X509Certificate certificate = null;
    private ServerCertificate serverCertificate = null;

    public ServerCertificateMapper(X509Certificate certificate) {
        this.certificate = certificate;
        this.serverCertificate = new ServerCertificate();
    }

    public ServerCertificate toServerCertificate(String serverName) {
        if (certificate != null) {
            Instant certificateNotAfter = certificate.getNotAfter().toInstant();
            Instant certificateNotBefore = certificate.getNotBefore().toInstant();
            Instant now = Instant.now();
            Duration duration = Duration.between(now, certificateNotAfter);
            long remains = duration.toDays();
            MatchService matchService = new MatchService(certificate);

            serverCertificate.setNotAfter(certificateNotAfter.getEpochSecond());
            serverCertificate.setNotBefore(certificateNotBefore.getEpochSecond());
            serverCertificate.setDistinguishedName(certificate.getSubjectDN().toString());
            serverCertificate.setRemains(remains);
            serverCertificate.setValidity(remains > 0L);
            serverCertificate.setMatch(matchService.find(serverName));
        }
        return serverCertificate;
    }
}
