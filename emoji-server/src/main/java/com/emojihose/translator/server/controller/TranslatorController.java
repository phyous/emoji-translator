package com.emojihose.translator.server.controller;

import com.emojihose.translator.server.Server;
import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import ro.pippo.controller.Controller;
import ro.pippo.core.Param;

public class TranslatorController extends Controller {

    Map<String, String> wordToEmojiMap;

    public TranslatorController() {
       this.wordToEmojiMap = Server.wordToEmojiMap;
    }

    public void translate(@Param("text") String text) {
        getResponse().text(translateSentence(text));
    }

    private String translateSentence(String sentence) {
        return Joiner.on(" ").join(
            Arrays.asList(sentence.split(" ")).stream()
            .map(x -> wordToEmojiMap.getOrDefault(x.toLowerCase(), x))
            .collect(Collectors.toList())
        );
    }
}
