package com.emojihose.translator.training;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class ModelBuilder {
    
    public static void main(String[] args) throws java.io.IOException {
        String emojiFile = args[0];
        String dictionaryFile = args[1];

        Map<String, List<String>> emojiMapping;
        Map<String, List<String>> wordGraph;
        try {
            emojiMapping = generateEmojiMapping(emojiFile);
            // TODO: We might want to also invert the mapping to open up the list of words to the values in the thesaurus
            wordGraph = generateDictioanryMapping(dictionaryFile);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Queue for our BFS to map words to emojis
        final Queue<String> workingQueue = new LinkedList<>();
        
        // The final model for emojis to words
        final Map<String, String> wordToEmojiMap = new HashMap<>();

        // Seed the wordToEmojiMap with words in emojiMapping
        emojiMapping.entrySet().stream()
            .forEach(x -> {
                final String emoji = x.getKey();
                final List<String> wordsAssociatedWithEmoji = x.getValue();
                processNewWords(emoji, wordsAssociatedWithEmoji, workingQueue, wordToEmojiMap);
            });
        
        // Process workingQueue to add new mappings of word -> emoji
        while(!workingQueue.isEmpty()) {
            
            String wordKey = workingQueue.remove();
            final String mappedEmoji = wordToEmojiMap.get(wordKey.toLowerCase());
            List<String> synonyms = wordGraph.getOrDefault(wordKey, ImmutableList.of());
            if (!synonyms.isEmpty()) {
                processNewWords(mappedEmoji, synonyms, workingQueue, wordToEmojiMap);    
            } 
        }
        
        System.out.println(translateSentence(args[2], wordToEmojiMap));
    }
    
    private static String translateSentence(String sentence, Map<String, String> wordToEmojiMap) {
        return Joiner.on(" ").join(
            Arrays.asList(sentence.split(" ")).stream()
            .map(x -> wordToEmojiMap.getOrDefault(x.toLowerCase(), x))
            .collect(Collectors.toList())
        );
    }
    
    private static void processNewWords(String emoji, 
                                       List<String> wordList, 
                                       Queue<String> queue, 
                                       Map<String, String> wordToEmojiMap) {
        wordList.stream().forEach(word -> {
            final String ret = wordToEmojiMap.putIfAbsent(word.toLowerCase(), emoji);
            if (ret == null) queue.add(word);
        });
        
    }
    
    private static Map<String, List<String>> generateEmojiMapping(String file) throws Exception {
        Map<String, List<String>> emojiMapping = new HashMap<>();
        
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] items = line.split(",");
                String emoji = items[0];
                List<String> words = Arrays.asList(items[1].split(";"));
                emojiMapping.put(emoji, words);
            }
        }
        
        return emojiMapping;
    }

    private static Map<String, List<String>> generateDictioanryMapping(String file) throws Exception {
        Map<String, List<String>> wordGraph = new HashMap<>();
        
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] items = line.split(",", 2);
                String key = items[0];
                List<String> words = Arrays.asList(items[1].split(","));
                wordGraph.put(key, words);
            }
        }
        
        return wordGraph;
    }
}
