package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AccountsPage {

    private WebDriver driver;
    private ElementUtil eleutil;
    private final By headers = By.xpath("//div[@id='content']//h2");
    private final By search = By.name("search");
    private final By searchIcon = By.xpath("//div[@id='search']//button");

    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        eleutil = new ElementUtil(driver);
    }

    public String getAccPageTitle() {
        String title = eleutil.waitForTitleIs(AppConstants.HOME_PAGE_TITLE,AppConstants.DEFAULT_TIMEOUT);
        System.out.println("Home Page Title is :"+title);
        return title;
    }

    public String getAccPageURL(){
        String title = eleutil.waitForURLContains(AppConstants.HOME_PAGE_FRACTION_URL,AppConstants.DEFAULT_TIMEOUT);
        System.out.println("Home Page URL :"+title);
        return title;
    }

    public List<String> getAccPageHeaders(){
       List<WebElement> headerList = eleutil.getElements(headers);
       List<String> headerValueList = new ArrayList<>();
       for(WebElement e : headerList){
            String text  = e.getText();
            headerValueList.add(text);
       }
        System.out.println(headerValueList);
        return headerValueList;
    }

    public SearchResultsPage doSearch(String searchKey){
        eleutil.doSendKeys(search,searchKey);
        eleutil.doClick(searchIcon);
        return new SearchResultsPage(driver);
    }



}
