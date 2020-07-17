package com.example.weatherApplication;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Data
public class WeatherVO {
    private String cityname;
    private Coord coord;
    private Weather weather;
    private Temp temp;
    @Data
    public class Coord{
        double lon;
        double lat;
    }
    @Data
    public class Weather{
        String main;
        String description;
    }
    @Data
    public class Temp{
        double temp_cur;
        double temp_min;
        double temp_max;
        int humidity;
    }
    public WeatherVO() {
        this.cityname = new String();
        this.coord = new Coord();
        this.weather = new Weather();
        this.temp = new Temp();
    }
    public WeatherVO(String cityname, Coord coord, Weather weather, Temp temp ) {
        this.cityname = cityname;
        this.coord = coord;
        this.weather = weather;
        this.temp = temp;
    }
}
