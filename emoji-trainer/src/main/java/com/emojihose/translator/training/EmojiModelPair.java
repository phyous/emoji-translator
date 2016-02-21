package com.emojihose.translator.training;

import java.util.List;

class EmojiModelPair {
    String emoji;
    List<String> path;

    public EmojiModelPair(String emoji, List<String> path) {
        this.emoji = emoji;
        this.path = path;
    }

    public String getEmoji() { return emoji; }
    public List<String> getPath() { return path; }
}