package com.currencyconverter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ConverterController {

    public static String date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

    @GetMapping("/convert")
    public ResponseEntity<String> convert(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam String amount
    ) throws Exception {

        //Set API endpoint and API key
        Properties properties = new Properties();
        properties.load(new FileInputStream("application.properties"));
        String accessKey = properties.getProperty("exchange.api.key");
        //String apiEndpoint = "latest";

        String url = "https://api.exchangeratesapi.io/v1/convert"
                + "?from=" + from
                + "&to=" + to
                + "&amount=" + amount
                + "&access_key=" + accessKey
                + "&date=" + date;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        return ResponseEntity.ok(response.body());
    }
}