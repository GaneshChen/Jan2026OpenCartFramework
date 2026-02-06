package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static com.qa.opencart.constants.AppConstants.*;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;

    private ElementUtil eleutil;

    //1.private By Locators
    private final By email = By.id("input-email");
    private final By password = By.id("input-password");
    private final By loginBtn = By.xpath("//input[@value='Login']");
    private final By forgotPwdLink = By.linkText("Forgotten Password");

    private final By registerLink = By.linkText("Register");

    private final By MyAccountTitle = By.xpath("//title[text()='My Account']");

    WebDriverWait wait;

    //2. public page constructors

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        eleutil = new ElementUtil(driver);

    }

    //3. Public Page actions/methods
    @Step("Getting Login Page Title")
    public String getLoginPageTitle(){
        String title = eleutil.waitForTitleIs(LOGIN_PAGE_TITLE,DEFAULT_TIMEOUT);
        System.out.println("Login Page Title :"+title);
        return title;
    }

    @Step("Getting Login Page URL")
    public String getLoginPageURL(){
       String url = eleutil.waitForURLContains(LOGIN_PAGE_FRACTION_URL,DEFAULT_TIMEOUT);
       System.out.println("Login Page Url :"+ url);
       return url;
    }

    @Step("Checking Forgot Pwd Link Exists or not")
    public boolean isForgotPwdLinkExist(){
        return eleutil.isElementDispalyed(forgotPwdLink);
    }

    @Step("Login with Valid UserName: {0} and Password: {1}")
    public AccountsPage doLogin(String userName, String pwd){
        System.out.println("User Credentials: "+userName + ":" + pwd);
        eleutil.waitForElementVisible(email,MEDIUM_DEFAULT_TIMEOUT).sendKeys(userName);
        eleutil.doSendKeys(password,pwd);
        eleutil.doClick(loginBtn);
        return new AccountsPage(driver);
    }

    @Step("Navigating to the Registration Page")
    public RegisterPage navigateToRegisterPage(){
        eleutil.clickWhenReady(registerLink, DEFAULT_TIMEOUT);
        return new RegisterPage(driver);
    }

}
