import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Properties;

public class Converter {
    public static void main(String[] args) throws Exception {
        //Set API endpoint and API key
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        String accessKey = properties.getProperty("api.key");
        String apiEndpoint = "latest";
        String date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

        String url = "https://api.exchangeratesapi.io/v1/" + apiEndpoint +
                "?access_key=" + accessKey + "&date=" + date;


        //Creating client
        HttpClient client = HttpClient.newHttpClient();

        //Building request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        System.out.println("Response: " + response.body());
        System.out.println("Status Code: " + response.statusCode());

        //Parsing to a map
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> exchangeRates = mapper.readValue(
                response.body(),
                new TypeReference<Map<String, Object>>(){}
        );

        //Getting rates
        Map<String, Object> rates = (Map<String, Object>) exchangeRates.get("rates");
        System.out.println("GBP Rate: " + rates.get("GBP"));
        System.out.println("USD Rate: " + rates.get("USD"));

    }


}