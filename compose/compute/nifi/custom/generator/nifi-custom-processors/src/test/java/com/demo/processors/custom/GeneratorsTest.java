package com.demo.processors.custom;

import com.demo.processors.custom.generators.string.BytesArrayGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;
public class GeneratorsTest {


    private static final ObjectMapper OM = new ObjectMapper();
    private static final String GENERATOR_CONFIG_FILE = "generator_config.json";

    @Test
    public void testGenerateBytes() {
        final String GENERATOR_NAME = "test_gen_bytes";
        BytesArrayGenerator generator = new BytesArrayGenerator(GENERATOR_NAME);
        Options generatorConfig = new Options(new HashMap<String, Object>(){{
            put("max_length", 20);
        }});
        generator.config(generatorConfig);
        for (int i = 0 ; i < 10 ; i ++) {
            Object data = generator.generate();
            System.out.println();
        }
    }

    private static DataProvider.FieldConfig[] parseConfig() throws IOException {
        URL configURL = GeneratorConfigTest.class.getClassLoader().getResource(GENERATOR_CONFIG_FILE);
        String configFilePath = Objects.requireNonNull(configURL).getPath();

        String generatorConfig = new String(Files.readAllBytes(Paths.get(configFilePath)));
        return OM.readValue(generatorConfig, DataProvider.FieldConfig[].class);
    }

}
