package com.example.weatherApplication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @PostMapping(path= "/hello", consumes = "application/json")
    public String addProductByPost(@RequestBody SkillPayload resultMap){
        log.info(resultMap.toString());
        String retStr = resultMap.getAction().getParams().get("name");
        //String name = ((LinkedHashMap)((LinkedHashMap)resultMap.getBody().get("action")).get("params")).get("name").toString();

        return retStr;
    }
}
