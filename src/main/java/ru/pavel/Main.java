package ru.pavel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main {

    static HttpGet request;
    static CloseableHttpResponse response;

    public static void main(String[] args) throws IOException {
        String url = "https://api.nasa.gov/planetary/apod?api_key=5elUpzVP9RDqcdiOlRUpggwyXEzZalt6MJGgPQp9";
        request = new HttpGet(url);
        String imageOut = "C:\\Users\\Laku\\Desktop\\image.jpg";

        NasaGet nasaGet = getContent();
        System.out.printf("высокое качество: %s\nнизкое качество: %s\n" +
                "описание: %s\n", nasaGet.getHdurl(), nasaGet.getUrl(), nasaGet.getExplanation());
        File file = new File(imageOut);
        file.createNewFile();
        InputStream inputStream = new URL(nasaGet.getHdurl()).openStream();
        Files.copy(inputStream, Paths.get(file.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);

    }

    static NasaGet getContent() {
        ObjectMapper mapper = new ObjectMapper();
        NasaGet list = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            response = httpClient.execute(request);
            list = mapper.readValue(response.getEntity().getContent(), NasaGet.class);
            response.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}