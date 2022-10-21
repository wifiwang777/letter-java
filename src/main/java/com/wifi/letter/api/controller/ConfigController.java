package com.wifi.letter.api.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Set;

@RestController
public class ConfigController {
    @ApolloConfig
    private Config config;

    @Autowired
    private Gson gson;

    @GetMapping("/config")
    public String index() {
        Set<String> keyNames = this.config.getPropertyNames();
        HashMap<String, String> map = new HashMap<>();
        for (String key : keyNames) {
            map.put(key, config.getProperty(key, ""));
        }
        return gson.toJson(map);
    }
}
