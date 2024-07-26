package com.coolbank.service;

import com.coolbank.model.AppComponent;

import java.util.UUID;

public interface AppComponentService {
    AppComponent registerComponent(AppComponent appComponent);

    AppComponent getComponentById(UUID componentId);

    AppComponent getComponentByName(String componentName);
}
