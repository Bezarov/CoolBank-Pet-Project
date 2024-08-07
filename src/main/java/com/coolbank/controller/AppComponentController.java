package com.coolbank.controller;

import com.coolbank.model.AppComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.coolbank.service.AppComponentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/components")
public class AppComponentController {
    private final Logger logger = LoggerFactory.getLogger(AppComponentController.class);
    private final AppComponentService appComponentService;

    public AppComponentController(AppComponentService appComponentService) {
        this.appComponentService = appComponentService;
    }

    @PostMapping
    public ResponseEntity<AppComponent> registerComponent(@RequestBody AppComponent appComponent) {
        logger.info("Received POST request to register Component: {}", appComponent);
        AppComponent responseAppComponent = appComponentService.registerComponent(appComponent);
        logger.debug("Request was successfully processed and response was sent: {}", responseAppComponent);
        return ResponseEntity.ok(responseAppComponent);
    }

    @GetMapping("/by-id/{componentId}")
    public ResponseEntity<AppComponent> getComponentById(@PathVariable UUID componentId) {
        logger.info("Received GET request to get Component by ID: {}", componentId);
        AppComponent responseAppComponent = appComponentService.getComponentById(componentId);
        logger.debug("Request was successfully processed and response was sent: {}", responseAppComponent);
        return ResponseEntity.ok(responseAppComponent);
    }

    @GetMapping("/by-name/{componentName}")
    public ResponseEntity<AppComponent> getComponentByName(@PathVariable String componentName) {
        logger.info("Received GET request to get Component by Name: {}", componentName);
        AppComponent responseAppComponent = appComponentService.getComponentByName(componentName);
        logger.debug("Request was successfully processed and response was sent: {}", responseAppComponent);
        return ResponseEntity.ok(responseAppComponent);
    }
}
