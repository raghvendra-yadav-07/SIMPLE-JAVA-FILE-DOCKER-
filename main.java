
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ClockServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(
            new InetSocketAddress("0.0.0.0", 8080), 0
        );

        server.createContext("/", exchange -> {
            String dateTime = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            );

            String response = """
                <html>
                  <head><title>Java Clock</title></head>
                  <body>
                    <h1>My Java Clock App</h1>
                    <p>Current date and time:</p>
                    <h2>%s</h2>
                  </body>
                </html>
                """.formatted(dateTime);

            byte[] body = response.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set(
                "Content-Type", "text/html; charset=UTF-8"
            );
            exchange.sendResponseHeaders(200, body.length);

            try (OutputStream output = exchange.getResponseBody()) {
                output.write(body);
            }
        });

        server.start();
        System.out.println("Java Clock running on port 8080");
    }
}
