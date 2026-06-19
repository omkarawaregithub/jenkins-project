package com.demo.app;

import com.jayway.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;  
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "spring.datasource.url=jdbc:h2:mem:java-webapp;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driver-class-name=org.h2.Driver", // Explicitly set H2 driver
        "spring.datasource.username=sa",                      // H2 default username
        "spring.datasource.password="                         // H2 default password (empty)
    })
public class HelloControllerTest
{
    @Autowired
    private JdbcTemplate jdbcTemplate; // Assuming you have this

    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/calc")
    public Map<String, Integer> calc(@RequestParam("left") int left, @RequestParam("right") int right) {
        // Your current query: SELECT 100 + 200 AS answer, which means you're only returning 'answer' from DB.
        // To match the test, you need 'left', 'right', and 'answer' in the JSON response.

        // Perform the calculation (or database query if it was more complex)
        int answer = left + right; // Direct calculation for simplicity

        // If you were doing a DB query like:
        // Integer answerFromDb = jdbcTemplate.queryForObject("SELECT ? + ? AS answer", Integer.class, left, right);
        // int answer = answerFromDb != null ? answerFromDb : 0; // Handle null if query returns nothing

        Map<String, Integer> result = new HashMap<>();
        result.put("left", left);
        result.put("right", right);
        result.put("answer", answer); // or answerFromDb

        return result;
    }
}
