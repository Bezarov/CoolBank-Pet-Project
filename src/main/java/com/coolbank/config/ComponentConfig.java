package com.coolbank.config;

import com.coolbank.model.AppComponent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ComponentConfig {
    @JsonProperty("components")
    List<AppComponent> components;

    public List<AppComponent> getComponents() {
        return components;
    }

    @Override
    public String toString() {
        return "ComponentConfig{" +
                "components=" + components +
                '}';
    }

    public static ComponentConfig readConfig() {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        ComponentConfig config = null;
        try {
            config = objectMapper.readValue(new File(
                    "src/main/resources/component-config.yml"), ComponentConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }
}
