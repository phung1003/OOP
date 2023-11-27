package com.example.demo;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;


public class Audio {

    private static int count = 0;

    public static String downloadMp3(String link) throws IOException {
        URLConnection conn = new URL(link).openConnection();
        InputStream is = conn.getInputStream();
        OutputStream outstream = new FileOutputStream(new File("file" + count + ".mp3"));
        count++;
        byte[] buffer = new byte[4096];
        int len;
        while ((len = is.read(buffer)) > 0) {
            outstream.write(buffer, 0, len);
        }
        outstream.close();
        String s = "file" + (count - 1) + ".mp3";
        return s;
    }

    public static void Delete() {
        for (int i = 0; i < count; i++) {
            File myObj = new File("file" + count + ".mp3");
            if (myObj.delete()) {
                System.out.println("Deleted the file: " + myObj.getName());
            } else {
                System.out.println("Failed to delete the file.");
            }
        }
        count = 0;
    }



}