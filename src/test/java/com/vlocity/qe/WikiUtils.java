package com.vlocity.qe;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class WikiUtils {
    public static int verifyURLStatus(String URL) {

        HttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build()).build();
        HttpGet request = new HttpGet(URL);

        try {
            HttpResponse response = client.execute(request);
            return response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            return 0;
        }
    }

    // Element finder final method
    public static WebElement languageWebElement(String location, ElementFinder finder) {
        return finder.findElement(By.xpath("//strong[contains(text(),'" + location + "')]"));
    }
}
