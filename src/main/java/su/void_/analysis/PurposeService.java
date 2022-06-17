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

package su.void_.analysis;

import java.security.cert.X509Certificate;

public class PurposeService {
    public static final int DIGITAL_SIGNATURE = 0;
    public static final int KEY_ENCIPHERMENT = 2;

    X509Certificate[] certificates = null;
    boolean[] purpose = null;
    boolean digitalSignature = false;
    boolean nonRepudiation = false;
    boolean keyEncipherment = false;
    boolean dataEncipherment = false;
    boolean keyAgreement = false;
    boolean keyCertSign = false;
    boolean cRLSign = false;
    boolean encipherOnly = false;
    boolean decipherOnly = false;

    public PurposeService(X509Certificate[] certificates) {
        this.certificates = certificates;
    }

    public X509Certificate findServerCertificate() {
        X509Certificate certificate = null;
        for (int i = 0; i < certificates.length; i++) {
            certificate = certificates[i];
            if (isServerCertificate(certificate)) {
                break;
            } else {
                certificate = certificates[0];
            }
        }
        return certificate;
    }

    private boolean isServerCertificate(X509Certificate certificate) {
        if (certificate == null) {
            return false;
        }
        purpose = certificate.getKeyUsage();
        if (purpose == null) {
            return false;
        }
        digitalSignature = purpose[0];
        nonRepudiation = purpose[1];
        keyEncipherment = purpose[2];
        dataEncipherment = purpose[3];
        keyAgreement = purpose[4];
        keyCertSign = purpose[5];
        cRLSign = purpose[6];
        encipherOnly = purpose[7];
        decipherOnly = purpose[8];
        if (isAbsent()) {
            return true;
        }
        if (isDigitalSignature()) {
            return true;
        }
        if (isKeyEncipherment()) {
            return true;
        }
        if (isAbsentExceptTwoIndex(PurposeService.DIGITAL_SIGNATURE, PurposeService.KEY_ENCIPHERMENT)) {
            return true;
        }
        return false;
    }

    private boolean isAbsent() {
        boolean isPurposeAbsent = true;
        for (int i = 0; i < purpose.length; i++) {
            if (true == purpose[i]) {
                isPurposeAbsent = false;
                break;
            }
        }
        return isPurposeAbsent;
    }

    private boolean isAbsentExceptIndex(int exceptIndex) {
        boolean isExceptIndex = false;
        if (purpose[exceptIndex]) {
            isExceptIndex = true;
        }
        boolean isPurposeAbsent = true;
        for (int i = 0; i < purpose.length; i++) {
            if ((i != exceptIndex) && (true == purpose[i])) {
                isPurposeAbsent = false;
                break;
            }
        }
        return (isExceptIndex && isPurposeAbsent);
    }

    private boolean isAbsentExceptTwoIndex(int exceptIndexPrimary, int exceptIndexSecondary) {
        boolean isExceptIndexPrimary = false;
        boolean isExceptIndexSecondary = false;
        if (purpose[exceptIndexPrimary]) {
            isExceptIndexPrimary = true;
        }
        if (purpose[exceptIndexSecondary]) {
            isExceptIndexSecondary = true;
        }
        boolean isPurposeAbsent = true;
        for (int i = 0; i < purpose.length; i++) {
            if ((i != exceptIndexPrimary) && (i != exceptIndexSecondary) && (true == purpose[i])) {
                isPurposeAbsent = false;
                break;
            }
        }
        return (isExceptIndexPrimary && isExceptIndexSecondary && isPurposeAbsent);
    }

    private boolean isDigitalSignature() {
        return isAbsentExceptIndex(PurposeService.DIGITAL_SIGNATURE);
    }

    private boolean isKeyEncipherment() {
        return isAbsentExceptIndex(PurposeService.KEY_ENCIPHERMENT);
    }
}
