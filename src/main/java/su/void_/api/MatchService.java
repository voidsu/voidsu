package su.void_.api;

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
                    System.out.println(serverName);
                    System.out.println(value);
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
