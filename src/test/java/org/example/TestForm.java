package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class TestForm {


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
    public void testEmptyNameField() {
        driver.get("https://www.uni-stuttgart.de/en/study/contact/");
        String title = driver.getTitle();

        Assertions.assertEquals("Points of contact for any questions about applying to study, or studying in general | University of Stuttgart", title);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        WebElement nameInput = driver.findElement(By.id("0_Name"));
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit' and @value='Submit']"));

        nameInput.clear();
        submitButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));

        WebElement errorSpan = driver.findElement(By.xpath("//span[@class='webform_label_error' and text()='This field is mandatory, please fill in a value.']"));

        Assertions.assertTrue(errorSpan.isDisplayed());

        // Verificar se o elemento <span> de erro está visível
        if (errorSpan.isDisplayed()) {
            System.out.println("O span de erro está sendo exibido corretamente.");
        } else {
            System.out.println("O span de erro NÃO está sendo exibido corretamente.");
        }
    }

    @Test
    public void testInvalidEmail() {
        driver.get("https://www.uni-stuttgart.de/en/study/contact/");
        String title = driver.getTitle();

        Assertions.assertEquals("Points of contact for any questions about applying to study, or studying in general | University of Stuttgart", title);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        WebElement emailInput = driver.findElement(By.id("2_Emailadresse"));
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit' and @value='Submit']"));

        emailInput.sendKeys("teste errado");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        submitButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        WebElement errorSpan = driver.findElement(By.xpath("//span[@class='webform_label_error' and text()='Invalid value, please correct your input']"));
        WebElement element = driver.findElement(By.id("2_Emailadresse"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);

        Assertions.assertTrue(errorSpan.isDisplayed());
        if (errorSpan.isDisplayed()) {
            System.out.println("O span de erro está sendo exibido corretamente.");
        } else {
            System.out.println("O span de erro NÃO está sendo exibido corretamente.");
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testeImageSliderNextChange() {
        driver.get("https://www.uni-stuttgart.de/en/");
        String title = driver.getTitle();

        Assertions.assertEquals("University of Stuttgart", title);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));

        WebElement buttonNext = driver.findElement(By.cssSelector(".carousel-control.right"));

        WebElement currentImage = driver.findElement(By.cssSelector(".carousel-inner .item.active img"));
        String currentImageSrc = currentImage.getAttribute("src");

        buttonNext.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement newImage = driver.findElement(By.cssSelector(".carousel-inner .item.active img"));
        String newImageSrc = newImage.getAttribute("src");

        Assertions.assertNotEquals(newImageSrc, currentImageSrc);

        if (!currentImageSrc.equals(newImageSrc)) {
            System.out.println("A imagem mudou com sucesso.");
        } else {
            System.out.println("A imagem não mudou.");
        }
    }

    @AfterAll
    public static void closeBrowser() {
        driver.quit();
    }
}