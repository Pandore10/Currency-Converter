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
        if (args.length == 0) {
            System.out.println("Usage: cconversor [options]");
            return;
        }

        if (args[0].contains("-h") || args[0].contains("--help")) {
            exibirAjuda();
            return;
        }

        if (args[0].contains("-l") || args[0].contains("--list")) {
            listarMoedas();
            return;
        }

        if (args[0].contains("-c") || args[0].contains("--convert")) {
            if (args.length != 4) {
                System.out.println("Error: Correct usage: -c <currency1> <currency2> <value>");
                System.out.println("Example: -c usd brl 100");
                return;
            }

            System.out.printf("Resultado: %s %s = %s %s", args[3], args[1], converterMoedas(args[1], args[2], args[3]), args[2]);
        }
    }

    public static void exibirAjuda() {
        System.out.println("Uso: cconversor [options]");
        System.out.println("Options");
        System.out.println("  -l --list                                               List currencies");
        System.out.println("  -c --convert <currency1> <currency2> <value> Convert currency1 into currency2");
        System.out.println("  -h --help                                               Show this list");
    }

    public static void listarMoedas() {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies.json"))
            .GET()
            .build();
        
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body().replace("{", "").replace("}", ""));

        } catch (Exception e) {
            System.err.println("Erro: " + e);
        }
    }

    public static String converterMoedas(String fromCurrency, String toCurrency, String value) {
        try {       
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/" + fromCurrency.toLowerCase() + ".json"))
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.body());

            Double resultado = Double.parseDouble(value) * rootNode.get(fromCurrency.toLowerCase()).get(toCurrency.toLowerCase()).asDouble();

            return String.format("%.2f", resultado); 
        } catch (Exception e) {
            System.err.println("Erro ao converter: " + e);
            return null;
        }
    }
    /*public static void main(String[] args) {
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
    }*/
}
