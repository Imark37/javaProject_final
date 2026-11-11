package org.example.common.config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigProvider {

    public static final AppConfig CONFIG = ConfigFactory.create(AppConfig.class, System.getProperties());

    public static void printConfig() {
        System.out.println("=== CONFIG ===");
        System.out.println("baseUrl         = " + CONFIG.baseUrl());
        System.out.println("apiUrl          = " + CONFIG.apiUrl());
        System.out.println("elementTimeout  = " + CONFIG.elementTimeout());
        System.out.println("==============");
    }
}