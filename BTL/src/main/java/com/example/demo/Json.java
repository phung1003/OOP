package com.example.demo;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Json {
    public static WordModel[] callApi(String word) throws IOException {
        URL urlObj = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + word);
        HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            System.out.println("OK");
            StringBuilder s = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNext()) {
                s.append(scanner.nextLine());
            }
            ObjectMapper objectMapper = new ObjectMapper();

            /* parse json*/
            WordModel[] wordModels = objectMapper.readValue(String.valueOf(s), new TypeReference<WordModel[]>() {
            });
            return wordModels;

        } else {
            System.out.println("CALL API ERROR " + responseCode);
            throw new IOException();

        }

    }

    public static void main(String[] args) throws IOException {
        WordModel[] wordModels = Json.callApi("hello");
        for (int i = 0; i < wordModels[0].phonetics.size(); i++) {
            System.out.println(wordModels[0].phonetics.get(i).text);
        }
    }

}
