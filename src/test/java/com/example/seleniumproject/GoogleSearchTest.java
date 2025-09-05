package com.example.seleniumproject;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class GoogleSearchTest {

    private WebDriver driver;


    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "C:/Users/malek/OneDrive/Documents/testmanagement/msedgedriver.exe");
        driver = new EdgeDriver();}
    @Test
    @Description("Test de connexion à l'application")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogin() {
        openLoginPage();
        enterCredentials("admin@biat.com", "Admin123!");
        clickLogin();
        verifyDashboardOpened();
    }

    @Step("Ouvrir la page de connexion")
    public void openLoginPage() {
        driver.get("http://localhost:4200/auth/login");
    }

    @Step("Saisir les identifiants")
    public void enterCredentials(String email, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("email1"))).sendKeys(email);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='password']"))).sendKeys(password);
    }

    @Step("Cliquer sur le bouton Login")
    public void clickLogin() {
        driver.findElement(By.xpath("//button[normalize-space()='Sign In']")).click();
    }

    @Step("Vérifier l'ouverture du Dashboard")
    public void verifyDashboardOpened() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/dashboard"));
    }
}




