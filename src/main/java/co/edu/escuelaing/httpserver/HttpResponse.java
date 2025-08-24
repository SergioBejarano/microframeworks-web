package co.edu.escuelaing.httpserver;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an HTTP response that will be sent back to the client.
 * Contains status, headers, and body.
 * 
 * @author sergio.bejarano-r
 */
public class HttpResponse {

    private int statusCode = 200;
    private String statusMessage = "OK";
    private String body = "";
    private final Map<String, String> headers = new HashMap<>();

    public HttpResponse() {
        headers.put("Content-Type", "text/plain; charset=UTF-8");
        headers.put("Connection", "close");
    }

    public void setStatus(int code, String message) {
        this.statusCode = code;
        this.statusMessage = message;
    }

    public void setBody(String body) {
        this.body = body;
        headers.put("Content-Length", String.valueOf(body.getBytes().length));
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    /**
     * Builds the full HTTP response as a String.
     */
    public String buildResponse() {
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusMessage).append("\r\n");
        headers.forEach((k, v) -> response.append(k).append(": ").append(v).append("\r\n"));
        response.append("\r\n");
        response.append(body);
        return response.toString();
    }
}
