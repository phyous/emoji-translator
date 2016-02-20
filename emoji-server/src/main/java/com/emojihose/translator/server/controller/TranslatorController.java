package com.emojihose.translator.server.controller;

import ro.pippo.controller.Controller;
import ro.pippo.core.Param;

public class TranslatorController extends Controller {

    public void translate(@Param("text") String text) {
        getResponse().text(text);
    }
}
