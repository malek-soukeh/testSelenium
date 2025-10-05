package com.example.seleniumproject;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class GoogleSearchTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() throws IOException {
        WebDriverManager.chromedriver().setup();
        Path tempProfile = Files.createTempDirectory("chrome-profile-");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Headless moderne
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--user-data-dir=" + tempProfile.toAbsolutePath());

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    @Description("Test E2E : Login et navigation vers la page Projets")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogin() throws InterruptedException {
        openLoginPage();
        enterCredentials("admin@biat.com", "Admin123!");
        clickLogin();
        verifyDashboardOpened();
        openProjectPage();
        createNewProject();
    }

    @Step("Ouvrir la page de connexion")
    public void openLoginPage() {
        driver.get("http://192.168.56.1:4200/auth/login"); // utiliser localhost pour tests locaux
    }

    @Step("Saisir les identifiants")
    public void enterCredentials(String email, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("email1"))).sendKeys(email);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='password']"))).sendKeys(password);
    }

    @Step("Cliquer sur le bouton Login")
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Sign In']"))).click();
    }

    @Step("Vérifier l'ouverture du Dashboard")
    public void verifyDashboardOpened() {
        wait.until(ExpectedConditions.urlContains("/dashboard"));
        try {
            WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.layout-menu-button")));
            menuButton.click();
        } catch (TimeoutException e) {
            System.out.println("Menu déjà ouvert ou bouton non présent.");
        }
    }

    @Step("Ouvrir la page Projets")
    public void openProjectPage() {
        // attendre que le menu soit visible
        WebElement projectsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[normalize-space()='Projects' and ancestor::a]")));
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", projectsLink);
        projectsLink.click();
        // attendre que la page Projects soit chargée
        wait.until(ExpectedConditions.urlContains("/dashboard/projects"));
    }

    @Step("Création d'un nouveau Projet")
    public void createNewProject() {
        WebElement createButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Create New Project']")));
        createButton.click();

        WebElement projectNameInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[placeholder='Enter project name...']")));
        projectNameInput.sendKeys("Projet Selenium Test");

        WebElement projectDescInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("textarea[placeholder^='Describe the project']")));
        projectDescInput.sendKeys("Description du projet pour test E2E Selenium");

        WebElement teamSizeInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[type='number']")));
        teamSizeInput.clear();
        teamSizeInput.sendKeys("5");

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Create Project')]")));
        submitButton.click();

    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}