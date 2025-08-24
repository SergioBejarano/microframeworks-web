package co.edu.escuelaing.httpserver;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Minimal web framework that supports REST services and static file serving.
 * 
 * @author sergio.bejarano-r
 */
public class HttpServer {

    private static final Map<String, Service> getRoutes = new HashMap<>();
    private static final Map<String, Service> postRoutes = new HashMap<>();
    private static String staticFilesDir;

    /**
     * Registers a service.
     *
     * @param path    URL path
     * @param service service implementation
     */
    public static void get(String path, Service service) {
        getRoutes.put("/app" + path, service);
    }

    /**
     * Registers a POST service.
     *
     * @param path    URL path
     * @param service service implementation
     */
    public static void post(String path, Service service) {
        postRoutes.put("/app" + path, service);
    }

    /**
     * Defines the folder for static files.
     *
     * @param dir path to static files directory
     */
    public static void staticFiles(String dir) {
        staticFilesDir = "target/classes" + dir;
    }

    /**
     * Starts the server.
     *
     * @param args CLI args (not used)
     * @throws Exception on socket error
     */
    public static void startServer(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            System.out.println("Server listening on port 8000...");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        OutputStream out = clientSocket.getOutputStream()) {

                    handleClient(in, out);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Handles a client connection.
     *
     * @param in  input stream from client
     * @param out output stream to client
     * @throws Exception on error
     */
    private static void handleClient(BufferedReader in, OutputStream out) throws Exception {
        String inputLine = in.readLine();
        if (inputLine == null || inputLine.isEmpty())
            return;

        String[] requestParts = inputLine.split(" ");
        String method = requestParts[0];
        URI uri = new URI(requestParts[1]);

        int contentLength = 0;
        String line;
        while (!(line = in.readLine()).isEmpty()) {
            if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.split(":")[1].trim());
            }
        }

        StringBuilder body = new StringBuilder();
        if ("POST".equalsIgnoreCase(method) && contentLength > 0) {
            char[] buf = new char[contentLength];
            in.read(buf, 0, contentLength);
            body.append(buf);
        }

        if ("GET".equalsIgnoreCase(method)) {
            handleGet(out, uri);
        } else if ("POST".equalsIgnoreCase(method)) {
            handlePost(out, uri, body.toString());
        } else {
            send404(out);
        }
    }

    private static void handleGet(OutputStream out, URI uri) throws IOException {
        String path = uri.getPath();

        if (getRoutes.containsKey(path)) {
            HttpRequest req = new HttpRequest("GET", uri, null);
            HttpResponse res = new HttpResponse();
            String body = getRoutes.get(path).executeService(req, res);
            res.setBody(body);
            out.write(res.buildResponse().getBytes());
            out.flush();
        } else {
            serveStaticFile(out, path);
        }
    }

    private static void handlePost(OutputStream out, URI uri, String requestBody) throws IOException {
        String path = uri.getPath();

        if (postRoutes.containsKey(path)) {
            HttpRequest req = new HttpRequest("POST", uri, requestBody);
            HttpResponse res = new HttpResponse();
            String body = postRoutes.get(path).executeService(req, res);
            res.setBody(body);
            out.write(res.buildResponse().getBytes());
            out.flush();
        } else {
            send404(out);
        }
    }

    private static void serveStaticFile(OutputStream out, String path) throws IOException {
        if (path.equals("/")) {
            path = "/index.html";
        }

        File file = new File(staticFilesDir + path);
        if (!file.exists() || file.isDirectory()) {
            send404(out);
            return;
        }

        String contentType = Files.probeContentType(file.toPath());
        byte[] content = Files.readAllBytes(file.toPath());

        String header = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + (contentType != null ? contentType : "application/octet-stream") + "\r\n" +
                "Content-Length: " + content.length + "\r\n" +
                "Connection: close\r\n\r\n";

        out.write(header.getBytes());
        out.write(content);
        out.flush();
    }

    private static void send404(OutputStream out) throws IOException {
        String msg = "<h1>404 Not Found</h1>";
        String header = "HTTP/1.1 404 Not Found\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + msg.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        out.write(header.getBytes());
        out.write(msg.getBytes());
        out.flush();
    }
}
