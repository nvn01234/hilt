package com.demo.processors.custom.generators.string;

import com.demo.processors.custom.Options;
import com.demo.processors.custom.generators.Generator;

public class BytesArrayGenerator implements Generator {

    public static final String CONFIG_MAX_LENGTH = "max_length";

    private final String name;
    private int maxLength;

    public BytesArrayGenerator(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void config(Options generatorConfig) {
        this.maxLength = generatorConfig
                .getIntOrDefaultWithValidator(CONFIG_MAX_LENGTH, 4, Options.Validator.POSITIVE_INTEGER_VALIDATOR);
    }

    @Override
    public Object generate() {
        final byte[] data = new byte[rd.nextInt(maxLength + 1)];
        rd.nextBytes(data);

        return data;
    }
}
