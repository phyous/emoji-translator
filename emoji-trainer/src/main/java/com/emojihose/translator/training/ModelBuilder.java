package com.emojihose.translator.training;

import com.emojihose.translator.training.helper.StringFunctions;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class ModelBuilder {
    
    public static void main(String[] args) throws java.io.IOException {
        final String emojiFile          = args[0];
        final String dictionaryFile     = args[1];
        final String partsOfSpeechFile  = args[2];
        final String outputFile         = args[3];

        ModelBuilder builder;

        System.out.println("=== Started building model");
        
        try {
          builder = new ModelBuilder(emojiFile, dictionaryFile, partsOfSpeechFile);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        final Map<String, EmojiModelPair> wordToEmojiMap = builder.build();

        PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
        wordToEmojiMap.entrySet().stream().forEach(x -> {
            EmojiModelPair pair = x.getValue();
            if(!pair.getEmoji().equals("*")) {
                String pathString = Joiner.on(";").join(pair.getPath());
                writer.println(x.getKey() + "," + pair.getEmoji() + "," + pathString);
            }
        });
        writer.close();
        System.out.println("=== FINISHED building model");
    }

    // The final model for emojis to words
    final Map<String, EmojiModelPair> wordToEmojiMap;
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

    public Map<String, EmojiModelPair> build() {
        // Seed the wordToEmojiMap with words in emojiMapping
        emojiMapping.entrySet().stream()
            .forEach(x -> {
                final String emoji = x.getKey();
                final List<String> path = new LinkedList<String>();
                final EmojiModelPair pair = new EmojiModelPair(emoji, path);
                final List<String> wordsAssociatedWithEmoji = x.getValue();
                processNewWords(pair, wordsAssociatedWithEmoji);
            });
        
        // Process workingQueue to add new mappings of word -> emoji
        while(!workingQueue.isEmpty()) {
            
            String wordKey = workingQueue.remove();
            final EmojiModelPair pair = wordToEmojiMap.get(wordKey.toLowerCase());
            final String mappedEmoji = pair.getEmoji();
            final List<String> pathCopy = new LinkedList<>(pair.getPath());
            pathCopy.add(0, wordKey);
            EmojiModelPair newPair = new EmojiModelPair(mappedEmoji, pathCopy);
            List<String> synonyms = wordGraph.getOrDefault(wordKey, ImmutableList.of());
            if (!mappedEmoji.equals("*")) {
                processNewWords(newPair, synonyms);
            } 
        }

        return wordToEmojiMap;
    }
    
    private void processNewWords(EmojiModelPair pair, List<String> wordList) {
        wordList.stream()
            .flatMap(StringFunctions.generateWordAndPlural)
            .forEach(word -> {
            if (!articles.contains(word.toLowerCase())) {
                final EmojiModelPair ret = wordToEmojiMap.putIfAbsent(word.toLowerCase(), pair);
                if (ret == null) workingQueue.add(word);
            }
        });
    }
    
    private Map<String, List<String>> generateEmojiMapping(String file) throws Exception {
        Map<String, List<String>> emojiMapping = new LinkedHashMap<>();
        
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
        Map<String, List<String>> wordGraph = new LinkedHashMap<>();
        
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] items = line.split(",", 2);
                String key = items[0];
                List<String> words = Arrays.asList(items[1].split(","));

                // Trim out any phrases from the thesaurus
                List<String> filteredWords = words.stream()
                        .filter( x -> !x.contains(" "))
                        .collect(Collectors.toList());

                wordGraph.put(key, filteredWords);
            }
        }
        
        return wordGraph;
    }

    private Set<String> generateArticlesSet(String file) throws Exception {
        Set<String> articles = new LinkedHashSet<>();

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
