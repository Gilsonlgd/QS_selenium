package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class JhuanTest {

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
    public void testVerifyLinks() {
        driver.get("https://www.uni-stuttgart.de/studium/studienangebot/?abschluss=all&fachrichtung=all");

        List<WebElement> links = driver.findElements(By.cssSelector(".studienkurzinfo a"));
        int linkCount = Math.min(links.size(), 1);

        for (int i = 0; i < linkCount; i++) {
            WebElement link = links.get(i);
            String url = link.getAttribute("href");
            if (url != null && !url.isEmpty()) {
                int responseCode = getResponseCode(url);
                assertTrue(responseCode == HttpURLConnection.HTTP_OK,
                        "O link em " + url + " não está acessível ou não está funcionando corretamente. Código de resposta: " + responseCode);
            }
        }
    }

    @Test
    public void testVerifyPhotos() {
        driver.get("https://www.uni-stuttgart.de/universitaet/");

        List<WebElement> photoLinks = driver.findElements(By.cssSelector(".fotostrecke-single a"));

        for (WebElement photoLink : photoLinks) {
            String photoUrl = photoLink.getAttribute("href");
            if (photoUrl != null && !photoUrl.isEmpty()) {
                int responseCode = getResponseCode(photoUrl);
                assertTrue(responseCode == HttpURLConnection.HTTP_OK,
                        "A foto em " + photoUrl + " não está acessível ou não está funcionando corretamente. Código de resposta: " + responseCode);
            }
        }
    }

    private int getResponseCode(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            return connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Test
    public void testActiveTab() {
        driver.get("https://www.uni-stuttgart.de/studium/bewerbung/online/"); 

        // Verifica qual tab está visível inicialmente
        WebElement firstTab = driver.findElement(By.id("id-eb0edcf0-0-parent"));
        WebElement secondTab = driver.findElement(By.id("id-eb0edcf0-1-parent"));
        WebElement thirdTab = driver.findElement(By.id("id-eb0edcf0-2-parent"));

        assertTrue(isTabActive(firstTab), "O primeiro elemento não está ativo inicialmente.");
        assertFalse(isTabActive(secondTab), "O segundo elemento está ativo inicialmente.");
        assertFalse(isTabActive(thirdTab), "O terceiro elemento está ativo inicialmente.");

        // Clica no segundo elemento
        secondTab.findElement(By.tagName("a")).click();

        assertFalse(isTabActive(firstTab), "O primeiro elemento ainda está ativo após clicar no segundo elemento.");
        assertTrue(isTabActive(secondTab), "O segundo elemento não está ativo após clicar nele.");
        assertFalse(isTabActive(thirdTab), "O terceiro elemento ainda está ativo após clicar no segundo elemento.");
    }

    private boolean isTabActive(WebElement tab) {
        String classValue = tab.getAttribute("class");
        return classValue.contains("active");
    }
    @Test
    public void testTippyTooltip() {
        driver.get("https://www.uni-stuttgart.de/studium/orientierung/try-science/");

        List<WebElement> buttons = driver.findElements(By.cssSelector(".linklist a[data-tippy-content]"));

        boolean tippyDisplayed = false;

        for (WebElement button : buttons) {
            String tippyContent = button.getAttribute("data-tippy-content");
            if (tippyContent != null && !tippyContent.isEmpty()) {
                Actions actions = new Actions(driver);
                actions.moveToElement(button).perform();

                String tippyId = button.getAttribute("aria-describedby");
                if (tippyId != null && tippyId.startsWith("tippy-")) {
                    WebElement tippyElement = driver.findElement(By.id(tippyId));

                    if (tippyElement.isDisplayed()) {
                        tippyDisplayed = true;
                        break;
                    }
                }
            }
        }

        assertTrue(tippyDisplayed, "O Tippy Tooltip está aparecendo ao passar o mouse sobre o botão.");
    }
}
