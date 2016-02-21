package com.emojihose.translator.server.helper;

import com.google.common.base.Joiner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EmojiMap {

    private Map<String, String> wordToEmojiMap;
    private Pattern patternWithSpaces;
    private Pattern patternNoSpaces;
    
    public EmojiMap(String mappingFile) {
        wordToEmojiMap = buildWordToEmojiMap(mappingFile);
        
        String pattern = Joiner.on("|").join(getWordToEmojiMap().entrySet().stream()
            .map(x -> x.getKey())
            .filter(x -> x.indexOf(" ") != -1 || x.indexOf("-") != -1)
            .map(x -> "(" + Pattern.quote(x) + ")")
            .collect(Collectors.toList()));
        String composite = "(?<!\\w)(?iu)(" + pattern + ")(?!\\w)";
        patternWithSpaces = Pattern.compile(composite);

        pattern = Joiner.on("|").join(getWordToEmojiMap().entrySet().stream()
            .map(x -> x.getKey())
            .filter(x -> x.indexOf(" ") == -1 && x.indexOf("-") == -1)
            .map(x -> "(" + Pattern.quote(x) + ")")
            .collect(Collectors.toList()));
        composite = "(?<!\\w)(?iu)(" + pattern + ")(?!\\w)";
        patternNoSpaces = Pattern.compile(composite);
    }
    
    private Map<String, String> buildWordToEmojiMap(String mappingFile) {
        Map<String, String> wordToEmojiMap = new HashMap<>();

        try(BufferedReader br = new BufferedReader(new FileReader(mappingFile))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] items = line.split(",");
                wordToEmojiMap.put(items[0], items[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return wordToEmojiMap;
        }
        return wordToEmojiMap;
    }

    public Map<String, String> getWordToEmojiMap() {
        return wordToEmojiMap;
    }

    public Pattern getPatternWithSpaces() {
        return patternWithSpaces;
    }

    public Pattern getPatternNoSpaces() {
        return patternNoSpaces;
    }
}
