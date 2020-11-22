package chess.AI;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Connector {

    public static String requestBestMove (String fen) {
        HttpClient client = HttpClient.newHttpClient();
        // GET
        HttpResponse<String> response = null;
        try {
            fen = fen.replace("/","!").replace(" ", "_");
            response = client.send(
                    HttpRequest
                            .newBuilder(new URI("http://localhost:8080/" + fen))
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofString()
            );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        int statusCode = response.statusCode();
        String body = response.body();
        System.out.println("status: "+ statusCode);
        System.out.println("best move: " + body);
        return body;
    }
}
