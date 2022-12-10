package ru.pavel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Main {

    static HttpGet request;
    static CloseableHttpResponse response;
    static NasaGet nasaGet;

    public static void main(String[] args) throws IOException {
        String url = "https://api.nasa.gov/planetary/apod?api_key=5elUpzVP9RDqcdiOlRUpggwyXEzZalt6MJGgPQp9";
        request = new HttpGet(url);

        nasaGet = getContent();
        String imageOut = nasaGet.getHdurl().substring(nasaGet.getHdurl().lastIndexOf("/") + 1);

        outFile(imageOut);

        System.out.printf("высокое качество: %s\nнизкое качество: %s\n" +
                "описание: %s\n", nasaGet.getHdurl(), nasaGet.getUrl(), nasaGet.getExplanation());
    }

    static void outFile(String imageOut) throws IOException {
        File file = new File(imageOut);
        file.createNewFile();
        InputStream inputStream = new URL(nasaGet.getHdurl()).openStream();
        Files.copy(inputStream, Paths.get(file.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);

        String imageOutTxt = imageOut.substring(imageOut.lastIndexOf("/") + 1, imageOut.indexOf(".")) + ".txt";
        File file1 = new File(imageOutTxt);
        file1.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file1.getAbsolutePath(), true))) {
            writer.write(nasaGet.getExplanation());
        } catch (IOException e) {

        }

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