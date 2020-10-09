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

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.security.cert.X509Certificate;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class X509CertificateV3Service {

    public boolean lookup(String address, Integer port, String serverName) {
        SSLSocket sslSocket = SocketService.createSslSocket(address, port, serverName);
        SSLSession session = sslSocket.getSession();
        Certificate[] chain = new Certificate[0];
        System.out.println(session.getId());
        System.out.println(session.getCipherSuite());
        System.out.println(session.getPeerHost());
        System.out.println(session.getPeerPort());
        //Arrays.stream(session.getValueNames()).forEach(s -> System.out.println(s));

        try {
            X509Certificate[] x509Certificates = session.getPeerCertificateChain();
            System.out.println(x509Certificates[0].getPublicKey().getAlgorithm());
            System.out.println(x509Certificates[0].getPublicKey().getFormat());
            System.out.println(x509Certificates[0].getSigAlgName());
            System.out.println(x509Certificates[0].getSigAlgOID());
            System.out.println(x509Certificates[0].getNotAfter());
            System.out.println(x509Certificates[0].getNotBefore());
            System.out.println(x509Certificates[0].getSubjectDN());
            System.out.println(x509Certificates[0].getVersion());

            System.out.println("----");

            Certificate[] certificates = session.getPeerCertificates();

            System.out.println("certificates: " + certificates.length);
            java.security.cert.CertificateFactory cf = java.security.cert.CertificateFactory.getInstance("X.509");
            byte[] binaryCertificate = certificates[1].getEncoded();
            ByteArrayInputStream stream = new ByteArrayInputStream(binaryCertificate);
            java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) cf.generateCertificate(stream);

//            Arrays.stream(x509Certificate.getSubjectAlternativeNames().toArray()).forEach(s -> System.out.println(s));
//            Arrays.stream(x509Certificate.getExtendedKeyUsage().toArray()).forEach(s -> System.out.println(s));

            System.out.println(x509Certificate.getIssuerAlternativeNames());
            System.out.println(x509Certificate.getIssuerX500Principal());
            System.out.println(x509Certificate.getSubjectX500Principal());
            System.out.println(x509Certificate.getSigAlgParams());
            System.out.println(x509Certificate.getSubjectDN());
            String[] keyUsageName = new String[] {
                "digitalSignature",
                "nonRepudiation",
                "keyEncipherment",
                "dataEncipherment",
                "keyAgreement",
                "keyCertSign",
                "cRLSign",
                "encipherOnly",
                "decipherOnly"
            };
            boolean[] keyUsage = x509Certificate.getKeyUsage();
            for (int i = 0; i < keyUsage.length; i++) {
                System.out.println(keyUsageName[i] + ": " + keyUsage[i]);
            }

//            Arrays.stream(x509Certificate.getIssuerAlternativeNames().toArray()).forEach(s -> System.out.println(s));

//            System.out.println(session.getPeerPrincipal());
//            System.out.println(session.getLocalPrincipal());
            //Arrays.stream(session.getPeerCertificateChain()).forEach(s -> System.out.println(s.getIssuerDN()));
            //Arrays.stream(session.getPeerCertificates()).forEach(s -> System.out.println(s.toString() + "\n\n----"));
        } catch (SSLPeerUnverifiedException | CertificateException e) {
            e.printStackTrace();
        }

//        try {
//            chain = session.getPeerCertificates();
//        } catch (SSLPeerUnverifiedException e) {
//            e.printStackTrace();
//        }
//        int certificateIndex = chain.length - 1;
//        X509Certificate certificate = (X509Certificate) chain[0];
        try {
            sslSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
