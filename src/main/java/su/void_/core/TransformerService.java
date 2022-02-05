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

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateEncodingException;

public class TransformerService {
    private CertificateFactory certificateFactory = null;

    public TransformerService() {
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }

    public X509Certificate[] transformToX509CertificateV3(Certificate[] certificateArray) {
        X509Certificate[] X509CertificateArray = new X509Certificate[certificateArray.length];
        for (int i = 0; i < certificateArray.length; i++) {
            X509CertificateArray[i] = toX509CertificateV3(certificateArray[i]);
        }
        return X509CertificateArray;
    }

    private X509Certificate toX509CertificateV3(Certificate certificate) {
        X509Certificate x509Certificate = null;
        ByteArrayInputStream inputStream = null;
        try {
            byte[] binaryCertificate = certificate.getEncoded();
            inputStream = new ByteArrayInputStream(binaryCertificate);
            x509Certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return x509Certificate;
    }
}
