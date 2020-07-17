package com.example.weatherApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@RestController
@RequestMapping("/weather")
@ConfigurationProperties(prefix="greeting")
public class WeatherController {
    @Autowired
    WeatherDAO wDAO;

    @Getter
    @Setter
    String hello;

    String returnString;

    @GetMapping("/getall")
    public List<WeatherVO> getAll() {
        return wDAO.getDatas();
    }

    @GetMapping("/get")
    public String getWeather(@RequestParam(value = "name", defaultValue = "Seoul") String cityname,
                             @RequestParam(value = "APIkey", defaultValue = "") String apikey) throws JsonProcessingException {

        HashMap<String, Object> result = new HashMap<String, Object>();
        WeatherVO w = new WeatherVO();

        try {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000); //타임아웃 설정 5초
            factory.setReadTimeout(5000);//타임아웃 설정 5초
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);

            String url = "http://api.openweathermap.org/data/2.5/weather";

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url+"?q="+cityname+"&appid="+apikey).build();

            //이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.
            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
            result.put("header", resultMap.getHeaders()); //헤더 정보 확인
            result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

            //데이터를 제대로 전달 받았는지 확인 string형태로 파싱해줌
            ObjectMapper mapper = new ObjectMapper();

            //데이터를 WeatherVO 형태로 저장해 weatherDAO에 넣어줌
            w.setCityname(cityname);
            w.getCoord().setLon(
                    Double.valueOf(((LinkedHashMap)resultMap.getBody().get("coord")).get("lon").toString())
            );
            w.getCoord().setLat(
                    Double.valueOf(((LinkedHashMap)resultMap.getBody().get("coord")).get("lat").toString())
            );
            w.getTemp().setTemp_cur(
                    Double.valueOf(((LinkedHashMap)resultMap.getBody().get("main")).get("temp").toString()) - 273.15
            );
            w.getTemp().setTemp_min(
                    Double.valueOf(((LinkedHashMap)resultMap.getBody().get("main")).get("temp_min").toString()) - 273.15
            );
            w.getTemp().setTemp_max(
                    Double.valueOf(((LinkedHashMap)resultMap.getBody().get("main")).get("temp_max").toString()) - 273.15
            );
            w.getTemp().setHumidity(
                    Integer.valueOf(((LinkedHashMap)resultMap.getBody().get("main")).get("humidity").toString())
            );
            w.getWeather().setMain(
                    ((LinkedHashMap)((ArrayList)resultMap.getBody().get("weather")).get(0)).get("main").toString()
            );
            w.getWeather().setDescription(
                    ((LinkedHashMap)((ArrayList)resultMap.getBody().get("weather")).get(0)).get("description").toString()
            );
            wDAO.addData(w);
            returnString = w.getCityname() + "의 현재 날씨는 " + w.getWeather().getDescription() + " 이고, 기온은 "
                    + Double.toString(w.getTemp().getTemp_cur()).substring(0,4) +"'C 이며, 습도는 " + w.getTemp().getHumidity() + "% 입니다. 좋은 하루 보내세요!";
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body"  , e.getStatusText());
            System.out.println("dfdfdfdf");
            System.out.println(e.toString());
        } catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body"  , "excpetion오류");
            System.out.println(e.toString());
        }
        return returnString;
    }
}
