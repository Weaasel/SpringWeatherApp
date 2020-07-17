# SpringWeatherApp

openweather API를 이용해 여러 지역의 날씨 및 기상정보를 알려주는 웹 어플리케이션입니다.



## Download

```
$git clone https://github.daumkakao.com/weasel-l/SpringWeatherApp.git
```



## Install

```
$./mvnw spring-boot:run
```



## 버전정보

### Ver1. REST API 입/출력

* 사용예시

  <img src="https://github.daumkakao.com/weasel-l/SpringWeatherApp/blob/master/WeatherApp_ver1.png?raw=true" width=500>

* Skill Set

  * RestController
    * 서버 역할의 Rest 통신
  * RestTemplate
    * 클라이언트 역할로 외부 API 를 호출하기 위함
  * Lombok
    * VO Class에 Getter, Setter를 지정해주기 위함

* Dependencies

  ```xml
  //Lombok
  	<dependency>
  			<groupId>org.projectlombok</groupId>
  			<artifactId>lombok</artifactId>
  			<optional>true</optional>
  		</dependency>
  
  //RestTemplate
  		<dependency>
  			<groupId>org.apache.httpcomponents</groupId>
  			<artifactId>httpclient</artifactId>
  		</dependency>
  ```



### Ver2. DB에 History 저장/조회 기능

* 추가 예정

### Ver3. Cache에 History 저장/조회 기능

* 추가 예정

  

## Class

### Main

* WeatherApplication
  * spring application을 Run 해주는 SpringBootApplication 클래스입니다.



### VO

* WeatherVO
  
  ```java
  public class WeatherVO {
      private String cityname; //도시 이름
      private Coord coord; //좌표
      private Weather weather; //날씨정보
      private Temp temp; //기온정보
      @Data
      public class Coord{
          double lon; //경도
          double lat; //위도
      }
      @Data
      public class Weather{
          String main; //간략 기상(ex.구름)
          String description; //세부 기상(ex. 구름 많이)
      }
      @Data
      public class Temp{
          double temp_cur; //현재 기온
          double temp_min; //최저 기온
          double temp_max; //최고 기온
          int humidity; //습도
      }
  ```
  
  

### DAO

* WeatherDAO
  * Weather들의 List를 관리하는 data access object
  * method
    * addData
    * getDatas



### Controller

* WeatherController
  * WeatherVO 객체에 대한 요청을 처리하는 controller입니다.
  * URI : "/weather"
  * method
    * getAll : 현재까지 호출한 모든 WeatherVO의 정보를 출력
    * getWeather : 도시의 이름과 openweather API key 를 입력받아 해당하는 날씨 정보를 출력

