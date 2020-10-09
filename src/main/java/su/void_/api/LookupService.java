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

public class LookupService {
    public static void main(String[] args) {
        (new LookupService()).lookup("void.su", 443, "void.su");
        (new LookupService()).lookup("mail.okd.su", 10443, "mail.okd.su");
        (new LookupService()).lookup("rckt.cc", 443, "rckt.cc");
        (new LookupService()).lookup("komarev.com", 443, "komarev.com");
        (new LookupService()).lookup("facebook.com", 443, "facebook.com");
        (new LookupService()).lookup("oracle.com", 443, "oracle.com");
        (new LookupService()).lookup("23.14.12.195", 443, "oracle.com");
        (new LookupService()).lookup("combats.su", 443, "combats.su");
        (new LookupService()).lookup("23.14.12.195", 443, "www.oracle.com");
    }

    public boolean lookup(String address, Integer port, String serverName) {
        SocketService socketService = new SocketService(address, port, serverName);
        socketService.create();
        ServerCertificate serverCertificate = socketService.fetchServerCertificate();
        socketService.close();

        System.out.println(serverCertificate.getNotAfter());
        System.out.println(serverCertificate.getNotBefore());
        System.out.println(serverCertificate.getDistinguishedName());
        System.out.println(serverCertificate.getRemains());
        System.out.println(serverCertificate.getValidity());
        // match certificate server name and request server name

        return true;
    }
}
