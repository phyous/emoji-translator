package com.emojihose.translator.training.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by pyoussef on 2/21/16.
 */
public class StringFunctions {

    public static final Function<String, Stream<String>> generateWordAndPlural = x -> {
        List<String> ret = new LinkedList<>();
        ret.add(x);
        if (x.endsWith("s")) ret.add(x + "es"); else ret.add(x + "s");
        return ret.stream();
    };
}
