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

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MatchService {
    public final static Integer DNS_NAME = 2;
    private X509Certificate certificate;

    public MatchService(X509Certificate certificate) {
        this.certificate = certificate;
    }

    public boolean find(String serverName) {
        boolean output = false;
        try {
            Collection<List<?>> subjectAltName = null;
            if (null != certificate.getSubjectAlternativeNames()) {
                subjectAltName = certificate.getSubjectAlternativeNames();
                Iterator<List<?>> subjectAltNameIterator = subjectAltName.iterator();
                while (subjectAltNameIterator.hasNext()) {
                    List<?> extensionList = subjectAltNameIterator.next();
                    Object[] extensions = extensionList.toArray();
                    Integer key = (Integer) extensions[0];
                    String value = (String) extensions[1];
                    if ((key == MatchService.DNS_NAME) && (serverName.equals(value))) {
                        output = true;
                        break;
                    }
                }
            }
        } catch (CertificateParsingException e) {
            e.printStackTrace();
        }
        return output;
    }
}
