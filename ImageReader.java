package com.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ImageReader {
    private int size;
    private String url;

    public void download(String imageUrl, String directory) {
        String savePath = this.buildSavePath(imageUrl, directory);

        // Download image from url and save to directory
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                String filePath = savePath + File.separator + fileName;

                try (InputStream inputStream = connection.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                this.setUrl(imageUrl);
                this.setSize((int) new File(filePath).length());
            } else {
                System.out.println("Failed to download: " + imageUrl + " - Response code: " + responseCode);
            }
        } catch (IOException e) {
            System.out.println("Error downloading: " + imageUrl);
            e.printStackTrace();
        }
    }

    private String buildSavePath(String imageUrl, String directory) {
        String host = imageUrl.split("/")[2];
        String path = imageUrl.substring(imageUrl.indexOf("/", 8), imageUrl.lastIndexOf("/"));
        String savePath = directory + File.separator + host + path;
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return savePath;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static void writeSortedImages(List<ImageReader> imageReaders, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (ImageReader imageReader : imageReaders) {
                writer.write(imageReader.getSize() + " " + imageReader.getUrl());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
