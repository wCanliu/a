package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PageReader {

    public List<String> getImageUrls(String pageUrl) {
        List<String> imageUrls = new ArrayList<>();
        try {
            URL url = new URL(pageUrl);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    imageUrls.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUrls;
    }
}
