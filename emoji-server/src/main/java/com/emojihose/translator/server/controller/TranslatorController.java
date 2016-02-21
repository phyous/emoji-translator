package com.emojihose.translator.server.controller;

import com.emojihose.translator.server.Server;
import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.pippo.controller.Controller;
import ro.pippo.core.Param;

public class TranslatorController extends Controller {

    private static final Logger log = LoggerFactory.getLogger(TranslatorController.class);
    
    Map<String, String> wordToEmojiMap;
    Pattern patternWithSpaces;
    Pattern patternNoSpaces;

    public TranslatorController() {
        this.wordToEmojiMap = Server.wordToEmojiMap;
        String pattern = Joiner.on("|").join(wordToEmojiMap.entrySet().stream()
            .map(x -> x.getKey())
            .filter(x -> x.indexOf(" ") != -1 || x.indexOf("-") != -1)
            .map(x -> "(" + Pattern.quote(x) + ")")
            .collect(Collectors.toList()));
        String composite = "(?<!\\w)(?iu)(" + pattern + ")(?!\\w)";
        patternWithSpaces = Pattern.compile(composite);

        pattern = Joiner.on("|").join(wordToEmojiMap.entrySet().stream()
            .map(x -> x.getKey())
            .filter(x -> x.indexOf(" ") == -1 && x.indexOf("-") == -1)
            .map(x -> "(" + Pattern.quote(x) + ")")
            .collect(Collectors.toList()));
        composite = "(?<!\\w)(?iu)(" + pattern + ")(?!\\w)";
        patternNoSpaces = Pattern.compile(composite);
    }

    public void translate(@Param("text") String text) {
        getResponse().text(translateSentence(text));
    }

    private String translateSentence(String sentence) {
        String ret = applyPattern(
            applyPattern(sentence, patternWithSpaces),
            patternNoSpaces
        );

        log.info(String.format("Translation from (%s): [%s] -> [%s]", getRequest().getClientIp(), sentence, ret));
        
        return ret;
    }

    private String applyPattern(String text, Pattern p) {
        Matcher m = p.matcher(text);
        String constructed = "";
        int lastIndex = 0;
        while(m.find()) {
            String word = m.group();
            constructed =
                constructed +
                text.substring(lastIndex, m.start()) +
                wordToEmojiMap.get(word.toLowerCase()) +
                text.substring(m.start() + word.length(), m.end());
            lastIndex = m.end();
        }

        return constructed + text.substring(lastIndex);
    }
}
