package com.demo.processors.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static com.demo.processors.custom.DataProvider.FieldConfig;

public class GeneratorConfigTest {

    private static final ObjectMapper OM = new ObjectMapper();
    private static final String GENERATOR_CONFIG_FILE = "generator_config.json";

    @Test
    public void testParsingConfig() throws IOException {
       FieldConfig[] fieldsConfig = parseConfig();

       // TODO: Verify config parsing
    }

    @Test
    public void testGenerateData() throws IOException {
        FieldConfig[] fieldsConfig = parseConfig();
        DataProvider provider = new DataProvider(fieldsConfig);

        String data = provider.generate(10);
        System.out.println();
    }

    private static FieldConfig[] parseConfig() throws IOException {
        URL configURL = GeneratorConfigTest.class.getClassLoader().getResource(GENERATOR_CONFIG_FILE);
        String configFilePath = Objects.requireNonNull(configURL).getPath();

        String generatorConfig = new String(Files.readAllBytes(Paths.get(configFilePath)));
        return OM.readValue(generatorConfig, FieldConfig[].class);
    }

}
