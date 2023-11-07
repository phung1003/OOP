package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;



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

//    public static String callTranslate(String text, boolean type) throws IOException {
//        //dịch từ anh sang việt
//        if (type == true) {
//            URL urlObj = new URL("http://127.0.0.1:5000/entovi");
//            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "multipart/form-data");
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                System.out.println("OK");
//                StringBuilder s = new StringBuilder();
//                Scanner scanner = new Scanner(connection.getInputStream());
//                while (scanner.hasNext()) {
//                    s.append(scanner.nextLine());
//                }
//                return s.toString();
//
//            } else {
//                System.out.println("CALL API ERROR " + responseCode);
//                throw new IOException();
//            }
//        } else { //dịch từ việt sang anh
//            URL urlObj = new URL("http://127.0.0.1:5000/vitoen");
//            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "multipart/form-data");
//            connection.setUseCaches(false);
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
//            wr.writeBytes("Content-Disposition: form-data; vi=\"" + text +"\"" + "\r\n");
//            wr.flush();
//            wr.close();
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                System.out.println("OK");
//                StringBuilder s = new StringBuilder();
//                Scanner scanner = new Scanner(connection.getInputStream());
//                while (scanner.hasNext()) {
//                    s.append(scanner.nextLine());
//                }
//                return s.toString();
//
//            } else {
//                System.out.println("CALL API ERROR " + responseCode);
//                throw new IOException();
//            }
//        }
//    }
    public static String callTranslate(String text, boolean type) throws IOException {
        if (type) {
            HttpPostMultipart multipart = new HttpPostMultipart("http://127.0.0.1:5000/entovi", "utf-8");
            multipart.addFormField("en", text);
            return multipart.finish();
        } else {
            HttpPostMultipart multipart = new HttpPostMultipart("http://127.0.0.1:5000/vitoen", "utf-8");
            multipart.addFormField("vi", text);
            return multipart.finish();
        }

    }

    public static void main(String[] args) throws IOException {
        WordModel[] wordModels = Json.callApi("dog");
        wordModels[0].removeDuplicates();
        System.out.println(wordModels[0].phonetic);
        for (int i = 0; i < wordModels[0].phonetics.size(); i++) {
            System.out.println(wordModels[0].phonetics.get(i).text);
            System.out.println(wordModels[0].phonetics.get(i).audio);
            System.out.println();
        }

    }

}
