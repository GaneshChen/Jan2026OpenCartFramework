package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static com.qa.opencart.constants.AppConstants.HOME_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.expectedAccPageHeadersList;

public class AccountsPageTest extends BaseTest {

    @BeforeClass
    public void accPageSetup(){
    accountsPage = loginPage.doLogin(properties.getProperty("username"), properties.getProperty("password"));

    }

    @Test
    public void accPageTitletest(){
        Assert.assertEquals(accountsPage.getAccPageTitle(), AppConstants.HOME_PAGE_TITLE);
    }

    @Test
    public void accPageURLTest(){
        Assert.assertTrue(accountsPage.getAccPageURL().contains(HOME_PAGE_FRACTION_URL));
    }

    @Test
    public void accPageHeadersTest(){
        System.out.println("The Expected Acc Page Header List :"+expectedAccPageHeadersList);
        List<String> actHeadersList =  accountsPage.getAccPageHeaders();
        Assert.assertEquals(actHeadersList,expectedAccPageHeadersList);
    }


}
