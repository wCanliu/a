package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Application {

    public static void main(String[] args) {
        String pageUrl = "http://10.122.7.154/javaweb/data/images-url.txt";
        String directory = "C:\\images";
        PageReader pageReader = new PageReader();
        List<String> imageUrls = pageReader.getImageUrls(pageUrl);
        List<ImageReader> imageReaders = new ArrayList<>();

        for (String imageUrl : imageUrls) {
            ImageReader imageReader = new ImageReader();
            imageReader.download(imageUrl, directory);
            imageReaders.add(imageReader);
        }

        // Sorting the imageReaders by size
        imageReaders.sort(Comparator.comparingInt(ImageReader::getSize));

        // Writing the sorted result to a file
        ImageReader.writeSortedImages(imageReaders, directory + "\\images-sorted.txt");
    }
}
