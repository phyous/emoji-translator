package com.emojihose.translator.server.helper;

public class EmojiMapSingleton {
    private static String fileArg;  
    
    public static void init(String file) {
        EmojiMapSingleton.fileArg = file;
    }

    private static EmojiMap instance = null;
    private EmojiMapSingleton() { }

    public static synchronized EmojiMap getInstance() {
        if (instance == null) {
            instance = new EmojiMap(EmojiMapSingleton.fileArg);
        }

        return instance;
    }
}
