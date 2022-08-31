package com.demo.processors.custom.generators.string;

import com.demo.processors.custom.Options;
import com.demo.processors.custom.generators.Generator;
import org.apache.nifi.components.Validator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TextGenerator implements Generator {

    public static final String CONFIG_MAX_WORDS = "max_words";
    public static final String CONFIG_ALLOWS_EMPTY = "allows_empty";
    public static final String CONFIG_ALLOWED_WORDS = "allowed";

    private final String name;

    private int maxWords;
    private boolean allowsEmpty;

    private String[] allowedWords;

    private static final String[] DICTIONARY = new String[] {
            "a", "ac", "accumsan", "ad", "adipiscing", "aenean", "aliquam", "aliquet", "amet", "ante", "aptent",
            "arcu", "at", "auctor", "augue", "bibendum", "blandit", "class", "commodo", "condimentum", "congue",
            "consectetur", "consequat", "conubia", "convallis", "cras", "cubilia", "curabitur", "curae", "cursus",
            "dapibus", "diam", "dictum", "dictumst", "dignissim", "dis", "dolor", "donec", "dui", "duis", "efficitur",
            "egestas", "eget", "eleifend", "elementum", "elit", "enim", "erat", "eros", "est", "et", "etiam", "eu",
            "euismod", "ex", "facilisi", "facilisis", "fames", "faucibus", "felis", "fermentum", "feugiat", "finibus",
            "fringilla", "fusce", "gravida", "habitant", "habitasse", "hac", "hendrerit", "himenaeos", "iaculis",
            "id", "imperdiet", "in", "inceptos", "integer", "interdum", "ipsum", "justo", "lacinia", "lacus", "laoreet",
            "lectus", "leo", "libero", "ligula", "litora", "lobortis", "lorem", "luctus", "maecenas", "magna", "magnis",
            "malesuada", "massa", "mattis", "mauris", "maximus", "metus", "mi", "molestie", "mollis", "montes", "morbi",
            "mus", "nam", "nascetur", "natoque", "nec", "neque", "netus", "nibh", "nisi", "nisl", "non", "nostra",
            "nulla", "nullam", "nunc", "odio", "orci", "ornare", "parturient", "pellentesque", "penatibus", "per",
            "pharetra", "phasellus", "placerat", "platea", "porta", "porttitor", "posuere", "potenti", "praesent",
            "pretium", "primis", "proin", "pulvinar", "purus", "quam", "quis", "quisque", "rhoncus", "ridiculus",
            "risus", "rutrum", "sagittis", "sapien", "scelerisque", "sed", "sem", "semper", "senectus", "sit",
            "sociosqu", "sodales", "sollicitudin", "suscipit", "suspendisse", "taciti", "tellus", "tempor", "tempus",
            "tincidunt", "torquent", "tortor", "tristique", "turpis", "ullamcorper", "ultrices", "ultricies", "urna",
            "ut", "varius", "vehicula", "vel", "velit", "venenatis", "vestibulum", "vitae", "vivamus", "viverra",
            "volutpat", "vulputate"
    };

    public TextGenerator(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void config(Options generatorConfig) {
        this.allowsEmpty = generatorConfig.getBoolOrDefault(CONFIG_ALLOWS_EMPTY, true);
        this.maxWords = generatorConfig
                .getIntOrDefaultWithValidator(CONFIG_MAX_WORDS, 1, Options.Validator.POSITIVE_INTEGER_VALIDATOR);
        this.allowedWords = generatorConfig.has(CONFIG_ALLOWED_WORDS) ?
                generatorConfig.getString(CONFIG_ALLOWED_WORDS).split(",") :
                null;
    }

    @Override
    public Object generate() {
        List<String> words = Arrays.asList(allowedWords != null ? allowedWords : DICTIONARY);
        Collections.shuffle(words);

        int numberOfWords = (allowsEmpty ? 0 : 1) + rd.nextInt(maxWords + 1);
        return numberOfWords == 0 ? "" :
                String.join(" ", words.subList(0, numberOfWords));
    }
}
