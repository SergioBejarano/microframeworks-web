package co.edu.escuelaing.httpserver;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an HTTP request received by the server.
 * Provides access to the request URI and query parameters.
 * 
 * @author sergio.bejarano-r
 */
public class HttpRequest {

    private final URI reqUri;
    private final Map<String, String> queryParams = new HashMap<>();
    private final String method;
    private final String body;

    /**
     * Constructs an HttpRequest from the method, URI, and body.
     *
     * @param method HTTP method (e.g., GET, POST)
     * @param reqUri The URI of the request
     * @param body   The body of the request (for POST requests)
     */
    public HttpRequest(String method, URI reqUri, String body) {
        this.method = method;
        this.reqUri = reqUri;
        this.body = body;
        parseQueryParams();
        parseQueryParams();
    }

    /**
     * Extracts query parameters from the URI and stores them in a map.
     */
    private void parseQueryParams() {
        String query = reqUri.getQuery();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }
        }
    }

    /**
     * Returns the value of a query parameter.
     *
     * @param paramName name of the parameter
     * @return value of the parameter or null if not found
     */
    public String getValue(String paramName) {
        return queryParams.get(paramName);
    }

    public URI getRequestUri() {
        return reqUri;
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }
}