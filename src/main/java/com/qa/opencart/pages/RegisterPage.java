package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {

    WebDriver driver;
    ElementUtil eleUtil;

    private By firstName = By.id("input-firstname");
    private By lastName = By.id("input-lastname");
    private By email = By.id("input-email");

    private By telephone = By.id("input-telephone");
    private By password = By.id("input-password");
    private By confrimpassword = By.id("input-confirm");

    private By subscribeYes = By.xpath("//label[@class='radio-inline'][position()=1]/input");

    private By subscribeNo = By.xpath("//label[@class='radio-inline'][position()=2]/input");

    private By agreeCheckBox = By.name("agree");

    private By ContinueBtn = By.xpath("//input[@type='submit' and @value='Continue']");

    private By registerLink = By.linkText("Register");
    private By logoutLink = By.linkText("Logout");
    private By successMessage = By.cssSelector("div#content h1");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    public boolean userRegistration(String firstName, String lastName,
                                   String telephone,String password,
                                  String subscribe){
        eleUtil.waitForElementVisible(this.firstName, AppConstants.DEFAULT_TIMEOUT).sendKeys(firstName);
        eleUtil.doSendKeys(this.lastName,lastName);
        eleUtil.doSendKeys(this.email, StringUtils.getRandomEmailId());
        eleUtil.doSendKeys(this.telephone,telephone);
        eleUtil.doSendKeys(this.password,password);
        eleUtil.doSendKeys(this.confrimpassword,password);

        if(subscribe.equalsIgnoreCase("yes")){
            eleUtil.doClick(subscribeYes);
        }
        else{
            eleUtil.doClick(subscribeNo);
        }

        eleUtil.doClick(agreeCheckBox);
        eleUtil.doClick(ContinueBtn);
        eleUtil.waitForElementVisible(successMessage,AppConstants.LONG_DEFAULT_TIMEOUT);
        if(eleUtil.doGetElementText(successMessage).contains(AppConstants.REGISTER_SUCCESS_MESSAGE)){
            eleUtil.doClick(logoutLink);
            eleUtil.doClick(registerLink);
            return true;
        }

        return false;
    }
}
