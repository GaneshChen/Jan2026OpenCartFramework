package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class SearchResultsPage {
    private WebDriver driver;
    private ElementUtil eleutil;
    private final By resultsProduct = By.cssSelector("div.product-thumb");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        eleutil = new ElementUtil(driver);
    }

    public int getResultsProductCount(){
    int searchCount = eleutil.waitForAllElementsPresence(resultsProduct, AppConstants.MEDIUM_DEFAULT_TIMEOUT).size();
    System.out.println("Total Number Of Search Products :"+searchCount);
    return searchCount;
   }

   public ProductInfoPage selectProducts(String productName){
       System.out.println("Product Name :"+productName);
       eleutil.waitForElementVisible(By.linkText(productName),AppConstants.MEDIUM_DEFAULT_TIMEOUT);
       eleutil.doClick(By.linkText(productName));
       return new ProductInfoPage(driver);
   }

}
