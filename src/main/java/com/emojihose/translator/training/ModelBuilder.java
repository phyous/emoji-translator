package com.emojihose.translator.training;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * Created by pyoussef on 2/20/16.
 */
public class ModelBuilder {
    
    public static void main(String[] args) {
        PrintWriter stdout = new PrintWriter(
            new OutputStreamWriter(System.out, StandardCharsets.UTF_8),
            true);

        stdout.println("\uD83D\uDE40");
        
    }
}
