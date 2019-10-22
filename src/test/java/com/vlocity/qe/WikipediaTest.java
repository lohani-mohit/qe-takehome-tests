package com.vlocity.qe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * This class verifies elements on the wikipedia homepage.
 */
public class WikipediaTest {

    private Logger log = LoggerFactory.getLogger(WikipediaTest.class);

    private WebDriver driver;
    private ElementFinder finder;

    @BeforeClass
    public void setup() {

        /*
            If the following driver version doesn't work with your Chrome version
            see https://sites.google.com/a/chromium.org/chromedriver/downloads
            and update it as needed.
        */

        /*As I am using chrome 77, we can also use just chromedriver().setup().
        cooked it your style (77.0.3865.40)*/
        WebDriverManager.chromedriver().version("77.0.3865.40").setup();
        driver = new ChromeDriver();
        finder = new ElementFinder(driver);
        driver.get("https://www.wikipedia.org/");

    }

    @Test
    public void sloganPresent() {

        String sloganClass = "localized-slogan";
        WebElement slogan = finder.findElement(By.className(sloganClass));

        Assert.assertNotNull(slogan, String.format("Unable to find slogan div by class: %s", sloganClass));

        log.info("Slogan text is {}", slogan.getText());

        Assert.assertEquals(slogan.getText(), "The Free Encyclopedia");
    }

    /* Req 2: Write a test method that verifies the languages are
     present by asserting their text value, based on a known
     list of languages.*/
    @Test(dataProvider = "data-provider", dataProviderClass = DataproviderClass.class)
    public void testLanguage(String data) {
        WebElement languageElement = WikiUtils.languageWebElement(data,finder);
        Assert.assertNotNull(languageElement, String.format("Unable to find the webelement for: %s", data));
        log.info("Language is {}", languageElement.getText());
        Assert.assertEquals(languageElement.getText(), data);
    }

    /* Req 3 : Write a test method that verifies the hyperlinks for the FeaturedLanguages work,
    that is, they return a HTTP 200 status.*/
    @Test(dataProvider = "data-provider", dataProviderClass = DataproviderClass.class)
    public void testLinks(String data) {
        WebElement languageElement = WikiUtils.languageWebElement(data,finder);
        {
            /*if element is not null get url and find status else throw Element not Found*/
            if (languageElement != null) {
                String languageURL = languageElement.findElement(By.xpath("..")).getAttribute("href");
                int status = WikiUtils.verifyURLStatus(languageURL);
                log.info("Status is {}", status);
                Assert.assertEquals(status, 200);
            } else {
                log.info("Cant find url as Element not found");
                Assert.fail(String.format("Element not found for : %s", data));
            }
        }
    }

    @AfterClass
    public void closeBrowser() {

        if (driver != null) {
            driver.close();
        }
    }


}
