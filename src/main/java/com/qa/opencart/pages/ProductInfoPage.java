package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

public class ProductInfoPage {
    WebDriver driver;
    ElementUtil eleUtil;

    //private By locators
    private final By productHeader = By.tagName("h1");
    private final By productImages = By.cssSelector("ul.thumbnails img");
    private final By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
    private final By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");

    private HashMap<String,String> productMap;
    public ProductInfoPage(WebDriver driver) {
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }
    public String getProductHeader(){
     String header = eleUtil.waitForElementVisible(productHeader, AppConstants.DEFAULT_TIMEOUT).getText();
     System.out.println("product header :"+header);
     return header;
    }

    public int getProductImagesCount(){
    int imageCount = eleUtil.waitForAllElementsVisible(productImages,AppConstants.LONG_DEFAULT_TIMEOUT).size();
    System.out.println("Total Number of Images :" + imageCount);
    return imageCount;
    }

    private void getProductMetaData(){
     List<WebElement> MetaList =eleUtil.waitForAllElementsVisible(productMetaData,AppConstants.MEDIUM_DEFAULT_TIMEOUT);
     for(WebElement e : MetaList){
         String metaData = e.getText();
         String[] meta = metaData.split(":");
         String metaKey = meta[0].trim();
         String metaValue = meta[1].trim();
         productMap.put(metaKey,metaValue);
         }
    }

    private void getProductPriceData(){
        List<WebElement> priceList = eleUtil.waitForAllElementsVisible(productPriceData,AppConstants.DEFAULT_TIMEOUT);
        String productPrice = priceList.get(0).getText();
        String exTaxPrice   =  priceList.get(1).getText().split(":")[1].trim();
        productMap.put("productprice",productPrice);
        productMap.put("extaxprice",exTaxPrice);
    }

    public HashMap<String,String> getProductDetailsMap(){
        productMap = new HashMap<>();
        productMap.put("productheader",getProductHeader());
        productMap.put("productimages",String.valueOf(getProductImagesCount()));
        getProductMetaData();
        getProductPriceData();
        System.out.println("Full Product Details :"+productMap);
        return productMap;
    }

}
