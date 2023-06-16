package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class FirstScriptTest {


    //objeto que cont�m os m�todos que controlam o navegador web
    private static WebDriver driver;
    private WebElement element;

    @BeforeAll
    public static void openBrowser(){
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\ChromeDriver\\chromedriver.exe");
        driver= new ChromeDriver();
        driver.manage().window().maximize();
        //Deleting all the cookies
        driver.manage().deleteAllCookies();
    }

    @Test
    public void eightComponents() {

        driver.get("https://google.com");
        String title = driver.getTitle();
        Assertions.assertEquals("Google", title);
        System.out.println(title);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        WebElement searchBox = driver.findElement(By.name("q"));
        WebElement searchButton = driver.findElement(By.name("btnK"));

        searchBox.sendKeys("Selenium");
        searchButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));


        searchBox = driver.findElement(By.name("q"));
        String value = searchBox.getAttribute("value");
        Assertions.assertEquals("Selenium", value);
        System.out.println(value);
        driver.quit();
    }
}
