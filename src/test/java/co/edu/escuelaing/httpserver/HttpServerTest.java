package co.edu.escuelaing.httpserver;

import org.junit.jupiter.api.*;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpServerTest {

    private ExecutorService executor;

    @BeforeAll
    void startServer() {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                HttpServer.staticFiles("/webroot");
                HttpServer.get("/hello", (req, res) -> {
                    res.setHeader("Content-Type", "text/plain");
                    return "Hello " + req.getValue("name");
                });
                HttpServer.get("/euler", (req, res) -> {
                    res.setHeader("Content-Type", "text/plain");
                    return String.valueOf(Math.E);
                });
                HttpServer.post("/echo", (req, res) -> {
                    res.setHeader("Content-Type", "text/plain");
                    return "Echo: " + req.getBody();
                });
                HttpServer.startServer(new String[] {});
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        boolean started = false;
        int attempts = 0;
        while (!started && attempts < 10) {
            try (Socket socket = new Socket("localhost", 8000)) {
                started = true;
            } catch (IOException e) {
                attempts++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
            }
        }
        if (!started) {
            throw new RuntimeException("El servidor no arrancó en el tiempo esperado.");
        }
    }

    @AfterAll
    void stopServer() {
        executor.shutdownNow();
    }

    private String sendHttpRequest(String request) throws IOException {
        try (Socket socket = new Socket("localhost", 8000)) {
            OutputStream out = socket.getOutputStream();
            out.write(request.getBytes(StandardCharsets.UTF_8));
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }
            return response.toString();
        }
    }

    @Test
    void shouldReturnHelloWorldOnGet() throws IOException {
        String request = "GET /app/hello?name=Sergio HTTP/1.1\r\nHost: localhost\r\n\r\n";
        String response = sendHttpRequest(request);
        assertTrue(response.contains("Hello Sergio"));
    }

    @Test
    void shouldReturnEulerOnGet() throws IOException {
        String request = "GET /app/euler HTTP/1.1\r\nHost: localhost\r\n\r\n";
        String response = sendHttpRequest(request);
        assertTrue(response.contains(String.valueOf(Math.E)));
    }

    @Test
    void shouldEchoOnPost() throws IOException {
        String body = "Hola desde POST";
        String request = "POST /app/echo HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "Content-Type: text/plain\r\n\r\n" +
                body;
        String response = sendHttpRequest(request);
        assertTrue(response.contains("Echo: Hola desde POST"));
    }

    @Test
    void shouldServeStaticFile() throws IOException {
        String request = "GET /index.html HTTP/1.1\r\nHost: localhost\r\n\r\n";
        String response = sendHttpRequest(request);
        assertTrue(response.contains("<!DOCTYPE html>"));
    }

    @Test
    void shouldReturnNotFoundOnInvalidPath() throws IOException {
        String request = "GET /app/invalid HTTP/1.1\r\nHost: localhost\r\n\r\n";
        String response = sendHttpRequest(request);
        assertTrue(response.contains("404 Not Found"));
    }
}
