package com.demo.processors.custom.generators.numeric;

import com.demo.processors.custom.Options;
import com.demo.processors.custom.generators.Generator;
import org.apache.nifi.components.Validator;

import java.util.HashMap;
import java.util.Map;

public class IntegerGenerator implements Generator {
    public static final String CONFIG_MAX_VALUE = "max_value";
    public static final String CONFIG_MIN_VALUE = "min_value";
    public static final String CONFIG_DISTRIBUTION = "distribution";
    public static final String CONFIG_DISTRIBUTION_ARGS = "distribution_args";

    private final String name;
    private long maxValue = Long.MAX_VALUE;
    private long minValue = Long.MIN_VALUE;

    public IntegerGenerator(String name) {
        this.name = name;
    }

    public static IntegerGenerator of(String name, long max, long min) {
        IntegerGenerator generator = new IntegerGenerator(name);
        generator.config(new Options()
                .with(CONFIG_MAX_VALUE, max)
                .with(CONFIG_MIN_VALUE, min));

        return generator;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void config(Options generatorConfig) {
        maxValue = generatorConfig.getLongOrDefault(CONFIG_MAX_VALUE, Long.MAX_VALUE);
        minValue = generatorConfig.getLongOrDefault(CONFIG_MIN_VALUE, Long.MIN_VALUE);

        // TODO: Distribution config
    }

    @Override
    public Object generate() {
        double mean = (maxValue + minValue) * 0.5;
        double fraction = rd.nextDouble();
        long value = fraction > 0.5 ?
                (long)((maxValue - mean) * (fraction - 0.5)) :
                (long)((mean - minValue) * fraction);

        return value;
    }
}
