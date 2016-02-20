package com.emojihose.translator.training;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by pyoussef on 2/20/16.
 */
public class ModelBuilder {
    
    public static void main(String[] args) throws java.io.IOException {
        PrintWriter stdout = new PrintWriter(
            new OutputStreamWriter(System.out, StandardCharsets.UTF_8),
            true);
        System.out.println(args.toString());

        File file = new File(args[0]);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] items = line.split(",");
            String emoji = items[0];
            String[] words = items[1].split(";");
            System.out.println(emoji);
            System.out.println(words.toString());
        }
    }
}
