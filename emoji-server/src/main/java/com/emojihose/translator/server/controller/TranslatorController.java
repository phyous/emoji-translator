package com.emojihose.translator.server.controller;

import com.emojihose.translator.server.Server;
import com.emojihose.translator.server.helper.EmojiMapSingleton;
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
    
    public void translate(@Param("text") String text) {
        getResponse().text(translateSentence(text));
    }

    private String translateSentence(String sentence) {
        String ret = applyPattern(
            applyPattern(sentence, EmojiMapSingleton.getInstance().getPatternWithSpaces()),
            EmojiMapSingleton.getInstance().getPatternNoSpaces()
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
                    EmojiMapSingleton.getInstance().getWordToEmojiMap().get(word.toLowerCase()) +
                text.substring(m.start() + word.length(), m.end());
            lastIndex = m.end();
        }

        return constructed + text.substring(lastIndex);
    }
}
