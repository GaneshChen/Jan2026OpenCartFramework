package com.qa.opencart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class HeadLessBrowser {

    public static void main(String[] args) {
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--headless");
        co.addArguments("--incognito");
        WebDriver driver = new ChromeDriver(co);
        driver.get("https://www.google.com");
        System.out.println(driver.getTitle());
        System.out.println(driver.getCurrentUrl());
        driver.quit();
    }
}
