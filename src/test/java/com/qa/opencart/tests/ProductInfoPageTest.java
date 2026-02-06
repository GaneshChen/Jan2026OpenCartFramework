package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CSVUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class ProductInfoPageTest extends BaseTest {

    @BeforeClass
    public void productInfoSetUp(){
    accountsPage = loginPage.doLogin(properties.getProperty("username"),properties.getProperty("password"));
    }

    @DataProvider
    public Object[][] getProductTestData(){
        return new Object[][]{
                {"macbook", "MacBook Pro"},
                {"macbook", "MacBook Air"},
                {"imac", "iMac"},
                {"samsung","Samsung SyncMaster 941BW"},
                {"samsung","Samsung Galaxy Tab 10.1"}
        };
    }

    @Test(dataProvider = "getProductTestData")
    public void productHeaderTest(String searchKey, String productName){
        searchResultsPage = accountsPage.doSearch(searchKey);
        productInfoPage = searchResultsPage.selectProducts(productName);
        String actHeader = productInfoPage.getProductHeader();
        Assert.assertEquals(actHeader,productName);
    }

    @DataProvider
    public Object[][] getProductImagesTestData(){
        return new Object[][]{
                {"macbook", "MacBook Pro",4},
                {"macbook", "MacBook Air",4},
                {"imac", "iMac",3},
                {"samsung","Samsung SyncMaster 941BW",1},
                {"samsung","Samsung Galaxy Tab 10.1",7}
        };
    }

    @DataProvider
    public Object[][] getProductCSVData(){
        return CSVUtil.csvData("product");
    }
    //@Test(dataProvider = "getProductImagesTestData")
    @Test(dataProvider = "getProductCSVData")
    public void productImageCountTest(String searchKey, String productName,String imagesCount){

        searchResultsPage = accountsPage.doSearch(searchKey);
        productInfoPage = searchResultsPage.selectProducts(productName);
        int actualImageCount = productInfoPage.getProductImagesCount();
        Assert.assertEquals(String.valueOf(actualImageCount),imagesCount);
    }

    @Test
    public void productInfoTest(){
        searchResultsPage = accountsPage.doSearch("macbook");
        productInfoPage = searchResultsPage.selectProducts("MacBook Pro");
        Map<String,String>acutalProductDetailsMap = productInfoPage.getProductDetailsMap();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(acutalProductDetailsMap.get("productheader"),"MacBook Pro");
        softAssert.assertEquals(acutalProductDetailsMap.get("productimages"),"4");
        softAssert.assertEquals(acutalProductDetailsMap.get("Brand"),"Apple");
        softAssert.assertEquals(acutalProductDetailsMap.get("Product Code"),"Product 18");
        softAssert.assertEquals(acutalProductDetailsMap.get("Availability"),"Out Of Stock");
        softAssert.assertEquals(acutalProductDetailsMap.get("productprice"),"$2,000.00");
        softAssert.assertEquals(acutalProductDetailsMap.get("extaxprice"),"$2,000.00");

        softAssert.assertAll(); // It will tell, how many got failed.
    }

}
