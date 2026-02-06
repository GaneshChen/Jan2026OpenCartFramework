package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.pages.SearchResultsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {
    @BeforeClass
    public void searchSetUp(){
       accountsPage = loginPage.doLogin(properties.getProperty("username"),properties.getProperty("password"));
    }

    @Test
    public void searchTest(){
        searchResultsPage = accountsPage.doSearch("airtel");
        int actResultsCount = searchResultsPage.getResultsProductCount();
        Assert.assertEquals(actResultsCount,0 );
    }





}
