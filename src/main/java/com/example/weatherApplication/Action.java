package com.example.weatherApplication;

import lombok.Data;

import java.util.Map;

@Data
public class Action {
    private String name;
    private String id;
    private Map<String, String> params;
}
