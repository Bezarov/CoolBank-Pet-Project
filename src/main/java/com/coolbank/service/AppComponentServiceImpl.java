package com.coolbank.service;

import com.coolbank.config.ComponentConfig;
import com.coolbank.model.AppComponent;
import com.coolbank.repository.AppComponentRepository;
import com.coolbank.security.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.UUID;

@Service
public class AppComponentServiceImpl implements AppComponentService {
    private static final Logger logger = LoggerFactory.getLogger(AppComponentServiceImpl.class);
    private final AppComponentRepository appComponentRepository;
    private final SecurityConfig security;

    public AppComponentServiceImpl(AppComponentRepository appComponentRepository, SecurityConfig security) {
        this.appComponentRepository = appComponentRepository;
        this.security = security;
    }

    @Override
    public AppComponent registerComponent(AppComponent appComponent) {
        logger.info("Attempting to find Component with ID: {}", appComponent.getComponentId());
        appComponentRepository.findById(appComponent.getComponentId())
                .ifPresent(AppComponentEntity -> {
                    logger.error("Component with such ID: {}, already registered", appComponent.getComponentId());
                    throw new ResponseStatusException(HttpStatus.FOUND,
                            "Component with such ID ALREADY REGISTERED: " + appComponent.getComponentId());
                });

        logger.info("Read/Refresh component-config.yml file");
        List<AppComponent> components = ComponentConfig.readConfig().getComponents();

        logger.info("Attempting to Compare incoming Component credentials with" +
                " configured Component credentials in component-config.yaml");
        AppComponent matchedIncomingComponentInComponentConfig = components.stream()
                .filter(component -> component.getComponentName().equals(appComponent.getComponentName()) &&
                        component.getComponentId().toString().equals(appComponent.getComponentId().toString()) &&
                        component.getComponentSecret().equals(appComponent.getComponentSecret()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Incoming credential was NOT FOUND in component-config.yml: {}", appComponent);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Invalid Component Credentials: " + appComponent);
                });

        logger.info("Encrypting component secret before save in DB");
        matchedIncomingComponentInComponentConfig.setComponentSecret(security.passwordEncoder()
                .encode(matchedIncomingComponentInComponentConfig.getComponentSecret()));

        logger.info("Component registered successfully: {}", matchedIncomingComponentInComponentConfig);
        return appComponentRepository.save(matchedIncomingComponentInComponentConfig);
    }

    @Override
    public AppComponent getComponentById(UUID componentId) {
        logger.info("Attempting to find Component with ID: {}", componentId);
        return appComponentRepository.findById(componentId)
                .map(AppComponentEntity -> {
                    logger.info("Component was found and received to the Controller: {}", AppComponentEntity);
                    return AppComponentEntity;
                })
                .orElseThrow(() -> {
                    logger.error("Component with such ID: {} was not found", componentId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Component with such ID was NOT Found: " + componentId);
                });
    }

    @Override
    public AppComponent getComponentByName(String componentName) {
        logger.info("Attempting to find Component with Name: {}", componentName);
        return appComponentRepository.findServiceByComponentName(componentName)
                .map(AppComponentEntity -> {
                    logger.info("Component was found and received to the Controller: {}", AppComponentEntity);
                    return AppComponentEntity;
                })
                .orElseThrow(() -> {
                    logger.error("Component with such Name: {} was not found", componentName);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Component with such Name was NOT Found: " + componentName);
                });
    }
}