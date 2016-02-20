package com.emojihose.translator.server;

import com.emojihose.translator.server.controller.TranslatorController;
import ro.pippo.controller.ControllerApplication;
import ro.pippo.core.Pippo;

public class Server {

    public static void main(String[] args) {
        Pippo pippo = new Pippo(new EmojiTranslatorApp());
        pippo.start();
    }

    static class EmojiTranslatorApp extends ControllerApplication {
        @Override
        protected void onInit() {
            GET("/translate", TranslatorController.class, "translate");
        }
    }
}
