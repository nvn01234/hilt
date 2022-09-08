package com.demo.processors.custom.generators;

import com.demo.processors.custom.Options;
import org.apache.nifi.components.Validator;

import java.util.Map;
import java.util.Random;

public interface Generator {
    Random rd = new Random();

    String name();

    void config(Options generatorConfig);

    Object generate();

}
