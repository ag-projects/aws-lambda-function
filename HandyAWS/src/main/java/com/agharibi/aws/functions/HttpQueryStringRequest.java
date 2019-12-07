package com.agharibi.aws.functions;


import java.util.HashMap;
import java.util.Map;

public class HttpQueryStringRequest {

    private Map<String, String> queryStringParameters = new HashMap<>();

    public Map<String, String> getQueryStringParameters() {
        return queryStringParameters;
    }

    public void setQueryStringParameters(Map<String, String> queryStringParameters) {
        this.queryStringParameters = queryStringParameters;
    }
}
