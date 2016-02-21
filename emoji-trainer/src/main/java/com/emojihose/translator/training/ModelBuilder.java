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
import java.util.HashSet;
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
        String partsOfSpeechFile = args[2];
        String outputFile = args[3];

        ModelBuilder builder;

        try {
          builder = new ModelBuilder(emojiFile, dictionaryFile, partsOfSpeechFile);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        final Map<String, String> wordToEmojiMap = builder.build();

        PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
        wordToEmojiMap.entrySet().stream().forEach(x -> {
            writer.println(x.getKey() + "," + x.getValue());
        });
        writer.close();
    }

    // The final model for emojis to words
    final Map<String, String> wordToEmojiMap;
    // Queue for our BFS to map words to emojis
    final Queue<String> workingQueue;

    final Map<String, List<String>> emojiMapping;
    final Map<String, List<String>> wordGraph;
    final Set<String> articles;

    public ModelBuilder(String emojiFile, String dictionaryFile, String partsOfSpeechFile) throws Exception {
        emojiMapping = generateEmojiMapping(emojiFile);
        // TODO: We might want to also invert the mapping to open up the list of words to the values in the thesaurus
        wordGraph = generateDictionaryMapping(dictionaryFile);
        articles = generateArticlesSet(partsOfSpeechFile);
        wordToEmojiMap = new HashMap<>();
        workingQueue = new LinkedList<>();
    }

    public Map<String, String> build() {
        // Seed the wordToEmojiMap with words in emojiMapping
        emojiMapping.entrySet().stream()
            .forEach(x -> {
                final String emoji = x.getKey();
                final List<String> wordsAssociatedWithEmoji = x.getValue();
                processNewWords(emoji, wordsAssociatedWithEmoji);
            });
        
        // Process workingQueue to add new mappings of word -> emoji
        while(!workingQueue.isEmpty()) {
            
            String wordKey = workingQueue.remove();
            final String mappedEmoji = wordToEmojiMap.get(wordKey.toLowerCase());
            List<String> synonyms = wordGraph.getOrDefault(wordKey, ImmutableList.of());
            if (!synonyms.isEmpty()) {
                processNewWords(mappedEmoji, synonyms);    
            } 
        }

        return wordToEmojiMap;
    }
    
    private void processNewWords(String emoji, List<String> wordList) {
        wordList.stream().forEach(word -> {
            if (!articles.contains(word.toLowerCase())) {
                final String ret = wordToEmojiMap.putIfAbsent(word.toLowerCase(), emoji);
                if (ret == null) workingQueue.add(word);
            }
        });
    }
    
    private Map<String, List<String>> generateEmojiMapping(String file) throws Exception {
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

    private Map<String, List<String>> generateDictionaryMapping(String file) throws Exception {
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

    private Set<String> generateArticlesSet(String file) throws Exception {
        Set<String> articles = new HashSet<>();

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] wordAndType = line.split("\\\\");
                String word = wordAndType[0];
                String typ = wordAndType[1];
                if(typ.indexOf("D") >= 0 || typ.indexOf("I") >= 0) {
                    articles.add(word.toLowerCase());
                }
            }
        }

        return articles;
    }
}
