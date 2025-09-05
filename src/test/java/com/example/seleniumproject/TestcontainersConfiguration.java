package com.example.seleniumproject;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.boot.test.context.TestConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    public static class EdgeSeleniumTest {

        @Test
        void openGoogle() {
            System.setProperty("webdriver.edge.driver", "C:/Users/malek/OneDrive/Documents/testmanagement/msedgedriver.exe");

            WebDriver driver = new EdgeDriver();
            driver.get("https://www.google.com");

            assertTrue(driver.getTitle().contains("Google"));

            driver.quit();
        }
    }
}
