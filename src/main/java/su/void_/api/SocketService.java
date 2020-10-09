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

import javax.net.ssl.*;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

public class SocketService {
    private String address = null;
    private Integer port = null;
    private String serverName = null;
    private SSLSocket sslSocket = null;
    private SSLSession sslSession = null;

    public SocketService(String address, Integer port, String serverName) {
        this.address = address;
        this.port = port;
        this.serverName = serverName;
    }

    private SSLParameters createSslParameters() {
        SSLParameters sslParameters = new SSLParameters();
        List<SNIServerName> sniHostNameList = Arrays.asList(new SNIHostName(serverName));
        sslParameters.setServerNames(sniHostNameList);
        return sslParameters;
    }

    public void create() {
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try {
            sslSocket = (SSLSocket) sslSocketFactory.createSocket(address, port);
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SSLParameters sslParameters = this.createSslParameters();
        sslSocket.setSSLParameters(sslParameters);
        sslSession = sslSocket.getSession();
    }

    public ServerCertificate fetchServerCertificate() {
        TransformerService transformerService = new TransformerService();
        X509Certificate serverCertificate = null;
        try {
            Certificate[] certificates = sslSession.getPeerCertificates();
            X509Certificate[] x509Certificates = transformerService.transformToX509CertificateV3(certificates);
            PurposeService purposeService = new PurposeService(x509Certificates);
            serverCertificate = purposeService.findServerCertificate();
        } catch (SSLPeerUnverifiedException e) {
            e.printStackTrace();
        }
        ServerCertificateMapper serverCertificateMapper = new ServerCertificateMapper(serverCertificate);
        return serverCertificateMapper.toServerCertificate();
    }

    public void close() {
        try {
            sslSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
