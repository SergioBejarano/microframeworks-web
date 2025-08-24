package co.edu.escuelaing.webexample;

import static co.edu.escuelaing.httpserver.HttpServer.*;

/**
 * 
 * WebApplication is the main entry point for the web application.
 * It sets up static file serving and defines RESTful endpoints.
 * 
 * @author sergio.bejarano-r
 */
public class WebApplication {

    public static void main(String[] args) throws Exception {
        staticFiles("/webroot");

        get("/hello", (req, resp) -> "Hello " + req.getValue("name"));

        get("/pi", (req, resp) -> {
            return String.valueOf(Math.PI);
        });

        post("/echo", (req, res) -> {
            return "<h1>Recibido en POST:</h1><pre>" + req.getBody() + "</pre>";
        });

        startServer(args);
    }
}
