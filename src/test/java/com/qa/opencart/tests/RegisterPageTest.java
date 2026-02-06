package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegisterPageTest extends BaseTest {

    @BeforeClass
    public void registerSetup(){
       registerPage = loginPage.navigateToRegisterPage();
    }

    @DataProvider
    public Object[][] getUserRegTestData(){
        return new Object[][]{
                {"Kol","Yannis","1230918761","Hello123","Yes"},
                {"Sharma","Rohit","1230918761","Hello123","Yes"},
                {"Archana","Sharma","1230918761","Hello123","Yes"}
        };
    }

    @DataProvider
    public Object[][] getUserRegExcelTestData(){
      Object[][] regData =  ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
        return regData;
    }

    //@Test(dataProvider = "getUserRegTestData")
    @Test(dataProvider ="getUserRegExcelTestData")
    public void userRegisterTest(String firstName, String lastName,String telephone,String password, String subscribe)
    {
        Assert.assertTrue(registerPage.userRegistration(firstName,lastName,telephone,password,subscribe));
    }



}
