package com.coolbank.controller;

import com.coolbank.model.AppComponent;
import com.coolbank.service.AppComponentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/components")
public class AppComponentController {
    private final AppComponentService appComponentService;

    public AppComponentController(AppComponentService appComponentService) {
        this.appComponentService = appComponentService;
    }

    @PostMapping
    public ResponseEntity<AppComponent> registerComponent(@RequestBody AppComponent appComponent) {
        AppComponent responseAppComponent = appComponentService.registerComponent(appComponent);
        return ResponseEntity.ok(responseAppComponent);
    }

    @GetMapping("/by-id/{componentId}")
    public ResponseEntity<AppComponent> getComponentById(@PathVariable UUID componentId) {
        AppComponent appComponent = appComponentService.getComponentById(componentId);
        return ResponseEntity.ok(appComponent);
    }

    @GetMapping("/by-name/{componentName}")
    public ResponseEntity<AppComponent> getComponentByName(@PathVariable String componentName) {
        AppComponent appComponent = appComponentService.getComponentByName(componentName);
        return ResponseEntity.ok(appComponent);
    }
}
