package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class SeleniumTest {
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
    public void testMainNavigation() {
        driver.get("https://www.iste.uni-stuttgart.de/");
        WebElement mainNavigationButton = driver.findElement(By.className("lines-button"));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
        mainNavigationButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        WebElement softwareLabMenuItem = driver.findElement(By.linkText("Software Lab"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        softwareLabMenuItem.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
        String urlEsperada = "https://www.software-lab.org/";
        String urlAtual = driver.getCurrentUrl();
        Assertions.assertEquals(urlEsperada, urlAtual);
    }

    @Test
    public void testLocationMap() {
        driver.get("https://www.iste.uni-stuttgart.de/");

        WebElement mainNavigationButton = driver.findElement(By.className("lines-button"));
        mainNavigationButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        WebElement instituteMenuItem = driver.findElement(By.linkText("Institute"));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement buldingElement = driver.findElement(By.cssSelector("span.map-tooltip#popup-map_2fefae9c"));
        String text = buldingElement.getText();
        String nomePredio = "CS building";
        Assertions.assertEquals(text, nomePredio);

    }

    @Test
    public void testNewsOrder() {
        driver.get("https://www.iste.uni-stuttgart.de/");

        WebElement Newselement = driver.findElement(By.cssSelector(".stream-teaser__component-headline"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", Newselement);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

        Assertions.assertArrayEquals(arrayDatasOrdenadas, arrayDatasRecebidas);

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

    @Test
    public void testEmptyNameField() {
        driver.get("https://www.uni-stuttgart.de/en/study/contact/");
        String title = driver.getTitle();

        Assertions.assertEquals("Points of contact for any questions about applying to study, or studying in general | University of Stuttgart", title);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        WebElement nameInput = driver.findElement(By.id("0_Name"));
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit' and @value='Submit']"));

        nameInput.clear();
        // Precisamos adicionar tempo antes de clicar no submit porque o site possui verificação
        // de tempo mínimo para preenchimento do formulário
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        submitButton.click();

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
    public void testInvalidEmailField() {
        driver.get("https://www.uni-stuttgart.de/en/study/contact/");
        String title = driver.getTitle();

        Assertions.assertEquals("Points of contact for any questions about applying to study, or studying in general | University of Stuttgart", title);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        WebElement emailInput = driver.findElement(By.id("2_Emailadresse"));
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit' and @value='Submit']"));

        emailInput.sendKeys("teste errado");
        // Usado para deixar o processo mais claro para o visualizador
        try {
            Thread.sleep(5000);
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

        // usado para deixar o processo mais claro para o visualizador
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testImageSliderNextChange() {
        driver.get("https://www.uni-stuttgart.de/en/");
        String title = driver.getTitle();

        Assertions.assertEquals("University of Stuttgart", title);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));

        WebElement buttonNext = driver.findElement(By.cssSelector(".carousel-control.right"));

        WebElement currentImage = driver.findElement(By.cssSelector(".carousel-inner .item.active img"));
        String currentImageSrc = currentImage.getAttribute("src");

        buttonNext.click();

        // usado para deixar o processo mais claro para o visualizador
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

    @Test
    public void testSocialMediaButtons() {
        driver.get("https://www.uni-stuttgart.de/en/");
        String title = driver.getTitle();

        Assertions.assertEquals("University of Stuttgart", title);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));

        WebElement facebookButton = driver.findElement(By.cssSelector(".linklist__link.is-facebook"));
        String hrefFacebook = facebookButton.getAttribute("href");
        Assertions.assertTrue(hrefFacebook.contains("facebook.com"));

        WebElement instagramButton = driver.findElement(By.cssSelector(".linklist__link.is-instagram"));
        String hrefInstagram = instagramButton.getAttribute("href");
        Assertions.assertTrue(hrefInstagram.contains("instagram.com"));

        WebElement twitterButton = driver.findElement(By.cssSelector(".linklist__link.is-twitter"));
        String hrefTwitter = twitterButton.getAttribute("href");
        Assertions.assertTrue(hrefTwitter.contains("twitter.com"));

        WebElement mastodonButton = driver.findElement(By.cssSelector(".linklist__link.is-mastodon"));
        String hrefMastodon = mastodonButton.getAttribute("href");
        Assertions.assertTrue(hrefMastodon.contains("xn--baw-joa.social"));

        WebElement youtubeButton = driver.findElement(By.cssSelector(".linklist__link.is-youtube"));
        String hrefYoutube = youtubeButton.getAttribute("href");
        Assertions.assertTrue(hrefYoutube.contains("youtube.com"));

        WebElement linkedinButton = driver.findElement(By.cssSelector(".linklist__link.is-linkedin"));
        String hrefLinkedin = linkedinButton.getAttribute("href");
        Assertions.assertTrue(hrefLinkedin.contains("linkedin.com"));

        WebElement ususButton = driver.findElement(By.cssSelector(".linklist__link.is-usus"));
        String hrefUsus = ususButton.getAttribute("href");
        Assertions.assertTrue(hrefUsus.contains("usus.uni-stuttgart"));
    }

    @Test
    public void testVerifyLinks() {
        driver.get("https://www.uni-stuttgart.de/studium/studienangebot/?abschluss=all&fachrichtung=all");

        List<WebElement> links = driver.findElements(By.cssSelector(".studienkurzinfo a"));
        int linkCount = Math.min(links.size(), 10);

        for (int i = 0; i < linkCount; i++) {
            WebElement link = links.get(i);
            String url = link.getAttribute("href");
            if (url != null && !url.isEmpty()) {
                int responseCode = getResponseCode(url);
                Assertions.assertTrue(responseCode == HttpURLConnection.HTTP_OK,
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
                Assertions.assertTrue(responseCode == HttpURLConnection.HTTP_OK,
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

        Assertions.assertTrue(isTabActive(firstTab), "O primeiro elemento não está ativo inicialmente.");
        Assertions.assertFalse(isTabActive(secondTab), "O segundo elemento está ativo inicialmente.");
        Assertions.assertFalse(isTabActive(thirdTab), "O terceiro elemento está ativo inicialmente.");

        // Clica no segundo elemento
        secondTab.findElement(By.tagName("a")).click();

        Assertions.assertFalse(isTabActive(firstTab), "O primeiro elemento ainda está ativo após clicar no segundo elemento.");
        Assertions.assertTrue(isTabActive(secondTab), "O segundo elemento não está ativo após clicar nele.");
        Assertions.assertFalse(isTabActive(thirdTab), "O terceiro elemento ainda está ativo após clicar no segundo elemento.");
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

        Assertions.assertTrue(tippyDisplayed, "O Tippy Tooltip não está aparecendo ao passar o mouse sobre o botão.");
    }
}
