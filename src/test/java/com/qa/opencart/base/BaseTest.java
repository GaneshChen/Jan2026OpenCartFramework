package com.qa.opencart.base;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.*;
import com.qa.opencart.utils.LogUtil;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.Properties;

//@Listeners(ChainTestListener.class)
public class BaseTest {
    WebDriver driver;
    DriverFactory driverFactory;
    protected LoginPage loginPage;
    protected AccountsPage accountsPage;
    protected SearchResultsPage searchResultsPage;
    protected ProductInfoPage productInfoPage;
    protected RegisterPage registerPage;

    protected Properties properties;

    @Description("init the driver and properties")
    @Parameters({"browser"})
    @BeforeTest   // @BeforeTest Means Before all the Test Cases.
    public void setup(String browserName){
        driverFactory = new DriverFactory();
        properties = driverFactory.initProp();

        if(browserName!=null){
            properties.setProperty("browser",browserName);
        }
        driver = driverFactory.initDriver(properties);
        loginPage = new LoginPage(driver);
    }

    @Description("Closing the Browser")
    @AfterTest    //@AfterTest means After All the Test Cases.
    @Step("First Level of Browser Quit")
    public void tearDown(){
        Allure.step("Chrome Browser Quitting!!!");
        driver.quit();
    }

//    @BeforeMethod
//    public void beforeMethod(ITestResult result){
//        LogUtil.info("=====Starting test case====="+result.getMethod().getMethodName());
//
//    }

    @AfterMethod
    public void attachScreenshot(ITestResult result){
        if(!result.isSuccess()){
            ChainTestListener.embed(DriverFactory.getScreenShotFile(),"image/png");
        }
        //LogUtil.info("=====Ending test case====="+result.getMethod().getMethodName());
    }
}
