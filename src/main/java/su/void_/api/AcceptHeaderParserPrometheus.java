package su.void_.api;

import java.util.*;

public class AcceptHeaderParserPrometheus {
    private String acceptHeader = null;
    private Map<String, List<String>> acceptMap = null;

    public AcceptHeaderParserPrometheus(String acceptHeader) {
        this.acceptHeader = acceptHeader;
        this.acceptMap = new HashMap<>();
    }

    public boolean isPrometheus() {
        List<String> mediaRange = Arrays.asList(acceptHeader.split(","));
        Iterator<String> iteratorMediaRange = mediaRange.iterator();
        while (iteratorMediaRange.hasNext()) {
            String mediaTypeWithParameters = iteratorMediaRange.next();
            List<String> mediaTypeWithParametersList = Arrays.asList(mediaTypeWithParameters.split(";"));
            String mediaType = mediaTypeWithParametersList.get(0).trim();
            List<String> mediaTypeParameterList = new ArrayList<>();
            if (mediaTypeWithParametersList.size() > 1) {
                for (int i = 1; i < mediaTypeWithParametersList.size(); i++) {
                    mediaTypeParameterList.add(mediaTypeWithParametersList.get(i).trim());
                }
            }
            acceptMap.put(mediaType, mediaTypeParameterList);
        }
        boolean isOpenMetrics = false;
        if (acceptMap.containsKey("application/openmetrics-text")) {
            List<String> parameterList = acceptMap.get("application/openmetrics-text");
            if (parameterList.contains("version=0.0.1")) {
                isOpenMetrics = true;
            }
        }
        boolean isTextPlain = false;
        if (acceptMap.containsKey("text/plain")) {
            List<String> parameterList = acceptMap.get("text/plain");
            if (parameterList.contains("version=0.0.4")) {
                isTextPlain = true;
            }
        }
        return (isOpenMetrics || isTextPlain);
    }
}
