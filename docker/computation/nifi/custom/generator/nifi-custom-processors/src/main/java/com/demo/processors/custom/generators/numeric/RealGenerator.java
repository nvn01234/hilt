package com.demo.processors.custom.generators.numeric;

import com.demo.processors.custom.Options;
import com.demo.processors.custom.generators.Generator;

public class RealGenerator implements Generator {

    public static final String CONFIG_MAX_VALUE = "max_value";
    public static final String CONFIG_MIN_VALUE = "min_value";
    public static final String CONFIG_DISTRIBUTION = "distribution";
    public static final String CONFIG_DISTRIBUTION_ARGS = "distribution_args";

    private final String name;
    private double maxValue = Long.MAX_VALUE;
    private double minValue = Long.MIN_VALUE;

    public RealGenerator(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void config(Options generatorConfig) {
        maxValue = generatorConfig.getDoubleOrDefault(CONFIG_MAX_VALUE, Double.MAX_VALUE);
        minValue = generatorConfig.getDoubleOrDefault(CONFIG_MIN_VALUE, Double.MIN_VALUE);

        // TODO: Distribution config
    }

    @Override
    public Object generate() {
        double mean = (maxValue + minValue) * 0.5;
        double fraction = rd.nextDouble();
        double value = fraction > 0.5 ?
                (maxValue - mean) * (fraction - 0.5) :
                (mean - minValue) * fraction;

        return value;
    }
}
