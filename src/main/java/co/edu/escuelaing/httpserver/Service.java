package co.edu.escuelaing.httpserver;

/**
 * Functional interface for REST services.
 * A service processes an HttpRequest and returns a response string.
 *
 * @author sergio.bejarano-r
 */
public interface Service {

    public String executeService(HttpRequest req, HttpResponse res);
}
