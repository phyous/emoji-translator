package com.emojihose.translator.server;

import com.emojihose.translator.server.controller.TranslatorController;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.pippo.controller.ControllerApplication;
import ro.pippo.core.Pippo;
import ro.pippo.core.PippoRuntimeException;
import ro.pippo.core.RedirectHandler;
import ro.pippo.core.TemplateHandler;
import ro.pippo.core.route.RouteContext;
import ro.pippo.core.route.RouteHandler;
import ro.pippo.freemarker.FreemarkerTemplateEngine;

import java.io.File;
import java.io.IOException;

public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static Map<String, String> wordToEmojiMap;
    
    public static void main(String[] args) {
        String mappingFile = args[0];
        wordToEmojiMap = new HashMap<>();

        try(BufferedReader br = new BufferedReader(new FileReader(mappingFile))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] items = line.split(",");
                wordToEmojiMap.put(items[0], items[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Pippo pippo = new Pippo(new EmojiTranslatorApp());
        pippo.getApplication().addPublicResourceRoute();
        pippo.start();
    }

    static class EmojiTranslatorApp extends ControllerApplication {
        @Override
        protected void onInit() {
            setTemplateEngine(new FreemarkerTemplateEngine());
            
            // Audit filter
            ALL("/.*", new RouteHandler() {
                @Override
                public void handle(RouteContext routeContext) {
                    log.info("Request for {} '{}'", routeContext.getRequestMethod(), routeContext.getRequestUri());
                    routeContext.next();
                }
            });

            // Web resources
            GET("/", new TemplateHandler("base"));
            
            // API
            GET("/api/translate", TranslatorController.class, "translate");
        }
    }
}
