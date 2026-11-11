package org.example.common.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources("classpath:config.properties")
public interface AppConfig extends Config {

    @Key("baseUrl")
    String baseUrl();

    @Key("apiUrl")
    String apiUrl();

    @Key("elementTimeout")
    long elementTimeout();

    @Key("admin.username")
    String adminUsername();

    @Key("admin.password")
    String adminPassword();
}