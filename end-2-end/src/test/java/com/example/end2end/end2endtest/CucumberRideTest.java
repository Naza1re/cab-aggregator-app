package com.example.end2end.end2endtest;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
        glue = "com/example/end2end/end2endtest")
public class CucumberRideTest {
}
