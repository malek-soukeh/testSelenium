package com.example.seleniumproject;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GoogleSearchTest {

    private WebDriver driver;


    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();}
    @Test
    @Description("Test E2E : Login et navigation vers la page Projets")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogin() {
        openLoginPage();
        enterCredentials("admin@biat.com", "Admin123!");
        clickLogin();
        verifyDashboardOpened();
        OpenProjectPage();
        CreateNewProject();

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
    @Step("Ouvrir la page Projets")
    public void OpenProjectPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement projectsMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Projects']")));
        projectsMenu.click();
    }

    @Step("Création d'un nouveau Projet")
    public void CreateNewProject() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement createButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Create New Project']")));
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        createButton.click();

        WebElement projectNameInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[placeholder='Enter project name...']")));
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        projectNameInput.sendKeys("Projet Selenium Test");

        WebElement projectDescInput = driver.findElement(
                By.cssSelector("textarea[placeholder^='Describe the project']"));
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        projectDescInput.sendKeys("Description du projet pour test E2E Selenium");

        WebElement teamSizeInput = driver.findElement(By.cssSelector("input[type='number']"));
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        teamSizeInput.clear();
        teamSizeInput.sendKeys("5");

        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Create Project')]"));
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
        submitButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[contains(text(),'Projet Selenium Test')]")));
        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
    }



}




