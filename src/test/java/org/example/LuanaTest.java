package org.example;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LuanaTest {
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
    public void testMainNavigation(){
        driver.get("https://www.iste.uni-stuttgart.de/");
        WebElement mainNavigationButton = driver.findElement(By.className("lines-button"));
        mainNavigationButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        WebElement softwareLabMenuItem = driver.findElement(By.linkText("Software Lab"));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        softwareLabMenuItem.click();
        String urlEsperada = "https://www.software-lab.org/";
        String urlAtual = driver.getCurrentUrl();
        assertEquals(urlEsperada, urlAtual);
    }

    @Test
    public void testLocationMap(){
        driver.get("https://www.iste.uni-stuttgart.de/");

        WebElement mainNavigationButton = driver.findElement(By.className("lines-button"));
        mainNavigationButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        WebElement instituteMenuItem = driver.findElement(By.linkText("Institute"));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        instituteMenuItem.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(20000));

        WebElement contactMenuItem = driver.findElement(By.linkText("Contact"));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        contactMenuItem.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        WebElement element = driver.findElement(By.id("map_2fefae9c"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        WebElement buldingElement = driver.findElement(By.cssSelector("span.map-tooltip#popup-map_2fefae9c"));
        String text = buldingElement.getText();
        String nomePredio = "CS building";
        assertEquals(text,nomePredio);

    }

    @Test
    public void testNewsOrder(){
        driver.get("https://www.iste.uni-stuttgart.de/");

        WebElement Newselement = driver.findElement(By.cssSelector(".stream-teaser__component-headline"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", Newselement);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));


        List<WebElement> dateElements = driver.findElements(By.cssSelector(".list-inline-meta li:nth-child(2)"));

        List<Date> datasEmDates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");


        for (WebElement dateElement : dateElements) {
            try {
                Date date = sdf.parse(dateElement.getText());
                datasEmDates.add(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        List<Date> datasOrdenadas = new ArrayList<>();

        for (Date dataRecida : datasEmDates) {
            datasOrdenadas.add(dataRecida);

        }

        Collections.sort(datasOrdenadas);
        Collections.reverse(datasOrdenadas);

        Date arrayDatasRecebidas[] = datasEmDates.toArray(new Date[datasEmDates.size()]);
        Date arrayDatasOrdenadas[] = datasOrdenadas.toArray(new Date[datasOrdenadas.size()]);

        assertArrayEquals(arrayDatasOrdenadas,arrayDatasRecebidas);


    }

    @AfterAll
    public static void closeBrowser() {
        driver.quit();
    }




}
