/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.pandore10.conversormoedas;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author pandore
 */
public class Conversormoedas {

    public static void main(String[] args) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://api.adviceslip.com/advice"))
        .GET()
        .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();

            JsonNode jsonNode = mapper.readTree(response.body());
            String advice = jsonNode.get("slip").get("advice").asText();
            System.out.println(advice);

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
