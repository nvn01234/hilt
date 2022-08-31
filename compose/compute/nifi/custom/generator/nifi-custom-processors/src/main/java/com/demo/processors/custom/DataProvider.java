package com.demo.processors.custom;

import com.demo.processors.custom.generators.*;
import com.demo.processors.custom.generators.datetime.TimestampGenerator;
import com.demo.processors.custom.generators.numeric.IntegerGenerator;
import com.demo.processors.custom.generators.numeric.RealGenerator;
import com.demo.processors.custom.generators.string.BytesArrayGenerator;
import com.demo.processors.custom.generators.string.TextGenerator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.nifi.components.Validator;

import java.util.*;

public class DataProvider {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final List<Generator> generators;
    
    private final Map<String, GeneratorProvider> generatorMapping = Collections.unmodifiableMap(
        new HashMap<String, GeneratorProvider>() {{
            put("int", DataProvider.this::createIntegerGenerator);
            put("real", DataProvider.this::createRealGenerator);
            put("bytes", DataProvider.this::createBytesArrayGenerator);
            put("text", DataProvider.this::createTextGenerator);
            put("timestamp", DataProvider.this::createTimestampGenerator);
        }}
    );

    /*
    * Config is presented by a json object and must be in format:
    * {
    *   {
    *       "name": <String>,
    *       "type": <int|bytes|text|timestamp>,
    *       "generator_config": {
    *           ...
    *       }
    *   }
    * }
    * */
    public DataProvider(FieldConfig[] config) {
        generators = new LinkedList<>();
        for(FieldConfig fieldConfig : config) {
            String name = fieldConfig.getName();
            String type = fieldConfig.getType();
            Options generatorConfig = new Options(fieldConfig.getGeneratorConfig());
            GeneratorProvider gProvider = generatorMapping.get(type);
            Generator generator = gProvider.createGeneratorProvider(name, generatorConfig);

            generators.add(generator);
        }
    }

    public String generate(int batchSize) throws JsonProcessingException {
        List<Map<String, Object>> generatedData = new LinkedList<>();
        for(int i = 0 ; i < batchSize ; i ++) {
            Map<String, Object> dataObject = new HashMap<>();
            for(Generator generator : generators) {
                dataObject.put(generator.name(), generator.generate());
            }
            generatedData.add(dataObject);
        }

        return OBJECT_MAPPER.writeValueAsString(generatedData);
    }

    private IntegerGenerator createIntegerGenerator(String name, Options generatorConfig) {
        IntegerGenerator generator = new IntegerGenerator(name);
        generator.config(generatorConfig);
        return generator;
    }

    private RealGenerator createRealGenerator(String name, Options generatorConfig) {
        RealGenerator generator = new RealGenerator(name);
        generator.config(generatorConfig);
        return generator;
    }

    private TextGenerator createTextGenerator(String name, Options generatorConfig) {
        TextGenerator generator = new TextGenerator(name);
        generator.config(generatorConfig);
        return generator;
    }

    private TimestampGenerator createTimestampGenerator(String name, Options generatorConfig) {
        TimestampGenerator generator = new TimestampGenerator(name);
        generator.config(generatorConfig);
        // TODO: Complete creating Timestamp generator
        return generator;
    }

    private BytesArrayGenerator createBytesArrayGenerator(String name, Options generatorConfig) {
        BytesArrayGenerator generator = new BytesArrayGenerator(name);
        generator.config(generatorConfig);
        return generator;
    }

    private interface GeneratorProvider {
        Generator createGeneratorProvider(String name, Options generatorConfig);
    }

    static class FieldConfig {
        private String name;

        private String type;

        @JsonProperty("generator_config")
        private Map<String, Object> generatorConfig;

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public Map<String, Object> getGeneratorConfig() {
            return null != generatorConfig ? generatorConfig : Collections.emptyMap();
        }
    }
}
