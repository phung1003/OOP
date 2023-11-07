package com.example.demo;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;


public class Audio {

    public static void downloadMp3(String link) throws IOException {
        URLConnection conn = new URL(link).openConnection();
        InputStream is = conn.getInputStream();

        OutputStream outstream = new FileOutputStream(new File("file.mp3"));
        byte[] buffer = new byte[4096];
        int len;
        while ((len = is.read(buffer)) > 0) {
            outstream.write(buffer, 0, len);
        }
        outstream.close();
    }

    public static void Delete(String name) {
        File myObj = new File(name);
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }



}