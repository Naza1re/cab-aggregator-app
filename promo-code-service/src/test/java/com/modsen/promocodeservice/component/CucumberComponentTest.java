package com.modsen.promocodeservice.component;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "com/modsen/promocodeservice/component"
)
public class CucumberComponentTest {
}
