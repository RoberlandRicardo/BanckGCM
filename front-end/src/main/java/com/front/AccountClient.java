package com.front;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.front.dto.AccountDTO;

public class AccountClient {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    private final HttpClient client;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public AccountClient() {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.baseUrl = "http://localhost:8080/banco";
    }

    public HttpResponse<String> registerAccount(AccountDTO accountDTO) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(accountDTO);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + "/conta"))
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> getAccountById(int accountId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + "/conta/" + accountId))
            .GET()
            .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> addCredit(int accountId, float amount) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + "/conta/" + accountId + "/" + amount + "/credito"))
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .PUT(HttpRequest.BodyPublishers.noBody())
            .build();
        
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> addDebit(int accountId, float amount) throws IOException, InterruptedException {
        String requestBody = "{\"amount\":" + amount + "}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + "/conta/" + accountId + "/" + amount + "/debito"))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> transfer(int sourceAccountId, int destinationAccountId, float amount) throws IOException, InterruptedException {
        String requestBody = String.format(Locale.US,
            "{\"from\": %d, \"to\": %d, \"amount\": %.2f}", 
            sourceAccountId, destinationAccountId, amount
        );
    
        System.out.println("Request Body: " + requestBody);
    
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + "/conta/transferencia"))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();
    
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    

    public HttpResponse<String> yieldInterest(int accountId, float rate) throws IOException, InterruptedException {
        // Construção da URL com os parâmetros na URL
        String url = String.format(Locale.US,"%s/conta/rendimento/%d/%f", baseUrl, accountId, rate);
        
        // Construção da solicitação HTTP PUT
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.noBody())  // Usando PUT e sem corpo
            .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
