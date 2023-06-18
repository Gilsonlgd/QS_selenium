package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class RobertoTest {

    private static WebDriver driver;

    @BeforeAll
    public static void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\ChromeDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    public static void closeBrowser() {
        driver.quit();
    }

    @Test
    public void searchBarWithNoTextLeadsToResultScreen() {
        driver.get("https://www.uni-stuttgart.de/en/");

        Assertions.assertEquals("University of Stuttgart", driver.getTitle());

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(6000));

        WebElement searchButton = driver.findElement(By.cssSelector("#search button"));
        Assertions.assertNotNull(searchButton);
        searchButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(6000));

        WebElement searchBoxButton = driver.findElement(By.cssSelector("form[action='https://www.uni-stuttgart.de/en/search/'] input[type='submit']"));
        Assertions.assertNotNull(searchBoxButton);
        searchBoxButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(6000));

        WebElement searchPageSort = driver.findElement(By.className("ap-searchsortcol"));
        Assertions.assertNotNull(searchPageSort);
    }

    @Test
    public void searchBarWithTextLeadsToResultScreen() {
        driver.get("https://www.uni-stuttgart.de/en/");

        Assertions.assertEquals("University of Stuttgart", driver.getTitle());

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(6000));

        WebElement searchButton = driver.findElement(By.cssSelector("#search button"));
        Assertions.assertNotNull(searchButton);
        searchButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(6000));

        WebElement searchBox = driver.findElement(By.name("q"));
        Assertions.assertNotNull(searchBox);
        searchBox.sendKeys("Brazil");

        WebElement searchBoxButton = driver.findElement(By.cssSelector("form[action='https://www.uni-stuttgart.de/en/search/'] input[type='submit']"));
        Assertions.assertNotNull(searchBoxButton);
        searchBoxButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(6000));

        WebElement searchPageSort = driver.findElement(By.className("ap-searchsortcol"));
        Assertions.assertNotNull(searchPageSort);
    }

    @Test
    public void languageChangeButtonChangesLanguageToGerman() {
        driver.get("https://www.uni-stuttgart.de/en/");

        Assertions.assertEquals("University of Stuttgart", driver.getTitle());

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(6000));

        WebElement languageMenu = driver.findElement(By.cssSelector("#language-menu-title"));
        Assertions.assertNotNull(languageMenu);
        languageMenu.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(6000));

        WebElement germanButton = driver.findElement(By.cssSelector("#localelink"));
        Assertions.assertNotNull(germanButton);
        germanButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(6000));

        Assertions.assertEquals("Universität Stuttgart", driver.getTitle());
    }

    @Test
    public void cardColorChangesOnHover() {
        driver.get("https://www.uni-stuttgart.de/en/");

        Assertions.assertEquals("University of Stuttgart", driver.getTitle());

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(6000));

        WebElement link;

        link = driver.findElement(By.cssSelector(".stream__wrapper > div:first-child > a"));
        Assertions.assertNotNull(link);
        final var firstColor = link.getCssValue("background-color");

        Actions actions = new Actions(driver);
        actions.moveToElement(link).perform();

        link = driver.findElement(By.cssSelector(".stream__wrapper > div:first-child > a"));
        Assertions.assertNotNull(link);
        final var secondColor = link.getCssValue("background-color");

        Assertions.assertNotEquals(firstColor, secondColor);
    }

}
