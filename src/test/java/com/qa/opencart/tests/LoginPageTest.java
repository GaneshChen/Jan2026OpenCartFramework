package com.qa.opencart.tests;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("F 50: Open Cart - Login Feature")
@Epic("Epic 100: Design Pages for open cart Application")
@Story("US 101: Implement Login Page for open cart Application")
public class LoginPageTest extends BaseTest
{

    @Description("Checking Open Cart Login Page Title....")
    @Severity(SeverityLevel.MINOR)
    @Owner("Ganesh")
    @Test
    public void loginPageTitleTest(){
        System.out.println("------Starting Test Cases-------");
        String acttitle = loginPage.getLoginPageTitle();
        ChainTestListener.log("The Title is :"+acttitle);
        Assert.assertEquals(acttitle, AppConstants.LOGIN_PAGE_TITLE);
        System.out.println("------Ending Test Cases--------");
    }

    @Description("Checking Open Cart Login Page URL....")
    @Severity(SeverityLevel.MINOR)
    @Owner("Ganesh")
    @Test
    public void loginPageURLTest(){
        String actURL = loginPage.getLoginPageURL();
        Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
    }

    @Description("Checking Open Cart Login Page as Forgot Pwd Link....")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ganesh")
    @Test
    public void forgotPwdLinkExistTest(){
       Assert.assertTrue(loginPage.isForgotPwdLinkExist());
    }

    @Description("Check user is able to login with valid user credentials ...")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Ganesh")
    @Test(priority = Short.MAX_VALUE)
    public void loginTest(){
        accountsPage =  loginPage.doLogin(properties.getProperty("username"), properties.getProperty("password"));
        Assert.assertEquals(accountsPage.getAccPageTitle(),AppConstants.HOME_PAGE_TITLE);
    }

}
