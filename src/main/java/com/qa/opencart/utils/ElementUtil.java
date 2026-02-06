package com.qa.opencart.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;


import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class ElementUtil {

    private WebDriver driver;
    private Actions act;

    public ElementUtil(WebDriver driver) {

        this.driver = driver;
        act = new Actions(driver);
    }

    @Step("Entering Value: {1} into element: {0}")
    public void doSendKeys(By locator, String value) {
        getElement(locator).clear();
        getElement(locator).sendKeys(value);
    }

    public void doSendKeys(By locator, CharSequence... value) {
        getElement(locator).sendKeys(value);
    }

    public void doClick(By locator) {
        getElement(locator).click();
    }

    @Step("Fetching element using: {0}")
    public String doGetElementText(By locator) {
        String eleText = getElement(locator).getText();
        if (eleText != null)
            return eleText;
        else {
            System.out.println("Element Text is null: " + eleText);
            return null;
        }
    }

    public boolean isElementDispalyed(By locator) {

        try {
            return getElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println("Element is NOT Displayed :" + locator);
            return false;
        }
    }

    public String doElementGetAttribute(By locator, String atrrName) {
        return getElement(locator).getAttribute(atrrName);
    }


    public boolean doSearch(By searchField, By suggestions, String searchKey, String matchValues) throws InterruptedException {

        boolean flag = false;
        doSendKeys(searchField, searchKey);

        List<WebElement> suggList = getElements(suggestions);
        int totalSuggestions = suggList.size();
        System.out.println("Total Number of Suggestions " + totalSuggestions);

        if (totalSuggestions == 0) {
            System.out.println("No Suggestions found....");
            //throw new FrameworkException("No Sugeestions Found");
        }

        for (WebElement e : suggList) {
            String text = e.getText();
            System.out.println(text);
            if (text.contains(matchValues)) {
                e.click();
                flag = true;
                break;
            }
        }
        if (flag) {
            System.out.println(matchValues + "is found");
            return true;
        } else {
            System.out.println(matchValues + "is NOT Found");
            return false;
        }
    }

    public WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    public int getElementsCount(By locator) {
        return getElements(locator).size();
    }

    public boolean isElementNotPresent(By locator) {
        if (getElementsCount(locator) == 0) {
            return true;
        }
        return false;
    }

    public boolean isElementPresentMultipleTimes(By locator) {
        if (getElementsCount(locator) >= 1) {
            return true;
        }
        return false;
    }

    public int getDropDownOptionsCount(By locator) {
        Select select = new Select(getElement(locator));
        return select.getOptions().size();

    }

    public void selectDropDownValueByVisibleText(By locator, String visibleText) {
        Select select = new Select(getElement(locator));
        select.selectByVisibleText(visibleText);
    }

    public void selectDropDownValueByIndex(By locator, int index) {
        Select select = new Select(getElement(locator));
        select.selectByIndex(index);
    }

    public void selectDropDownValueByValue(By locator, String value) {
        Select select = new Select(getElement(locator));
        select.selectByValue(value);
    }

    public void selectDropDownValue(By locator, String Value) {
        List<WebElement> optionsList = getElements(locator);
        System.out.println(optionsList.size());
        for (WebElement e : optionsList) {
            String text = e.getText();
            if (text.equals(Value)) {
                e.click();
                break;
            }
        }
    }

    public void ParentChildMenu(By level1,By level2, By level3, By level4) throws InterruptedException {

        doClick(level1);
        Thread.sleep(1000);
        act.moveToElement(getElement(level2)).perform();
        Thread.sleep(1000);
        act.moveToElement(getElement(level3)).perform();
        Thread.sleep(1000);
        doClick(level4);

    }

    // ************Actions Utils************//

    public void doMoveToElement(By locator) throws InterruptedException {
        act.moveToElement(getElement(locator)).build().perform();
        Thread.sleep(2000);
    }

    public void handleParentSubMenu(By parentMenu, By subMenu) throws InterruptedException {
        doMoveToElement(parentMenu);
        doClick(subMenu);
    }

    public void handle4LevelMenuHandle(By level1Menu, By level2Menu, By level3Menu, By level4Menu)
            throws InterruptedException {
        doClick(level1Menu);
        Thread.sleep(2000);
        doMoveToElement(level2Menu);
        Thread.sleep(2000);
        doMoveToElement(level3Menu);
        Thread.sleep(2000);
        doClick(level4Menu);
    }

    public void doActionsSendKeys(By locator, String value) {
        act.sendKeys(getElement(locator), value).perform();
    }

    public void doActionsClick(By locator) {
        act.click(getElement(locator)).perform();
    }

    public void doSendKeysWithPause(By locator, String value, long pauseTime) {
        char val[] = value.toCharArray();
        for (char ch : val) {
            act.sendKeys(getElement(locator), String.valueOf(ch)).pause(pauseTime).perform();
        }
    }

    // Wait Utils**************//

    /**
     * An expectation for checking that there is at least one element present on a
     * web page.
     *
     * @param locator
     * @param timeOut
     * @return
     */
    public List<WebElement> waitForAllElementsPresence(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

        try{
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        }catch(TimeoutException e){
            return Collections.EMPTY_LIST;
        }


    }

    /**
     * An expectation for checking that all elements present on the web page that
     * match the locator are visible. Visibility means that the elements are not
     * only displayed but also have a height and width that is greater than 0.
     *
     * @param locator
     * @param timeOut
     * @return
     */
//    public List<WebElement> waitForAllElementsVisible(By locator, int timeOut) {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
//        try {
//            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
//        } catch (TimeoutException e) {
//            return Collections.EMPTY_LIST; //[]-0
//        }
//    }

    /**
     * An expectation for checking that an element is present on the DOM of a page.
     * This does not necessarily mean that the element is visible.
     *
     * @param locator
     * @param timeOut
     * @return
     */
    public WebElement waitForElementPresence(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // *****wait for alert(JS POP)*****//
    public Alert waitForAlert(int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.pollingEvery(Duration.ofSeconds(1)).ignoring(NoAlertPresentException.class)
                .withMessage("===js alert not present");
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public void acceptAlert(int timeOut) {
        waitForAlert(timeOut).accept();
    }

    public void dismissAlert(int timeOut) {
        waitForAlert(timeOut).dismiss();
    }

    public String getTextAlert(int timeOut) {
        return waitForAlert(timeOut).getText();
    }

    public void sendKeysAlert(int timeOut, String value) {
        waitForAlert(timeOut).sendKeys(value);
    }


    // wait for title:
    public String waitFotTitleContains(String fractionTitle, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.titleContains(fractionTitle));
            return driver.getTitle();

        } catch (TimeoutException e) {
            return null;
        }

    }

    public String waitForTitleIs(String title, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.titleIs(title));
            return driver.getTitle();

        } catch (TimeoutException e) {
            return null;
        }

    }

    // wait for url:
    public String waitForURLContains(String fractionURL, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.urlContains(fractionURL));
            return driver.getCurrentUrl();

        } catch (TimeoutException e) {
            return null;
        }

    }

    public String waitForURLIs(String url, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.urlToBe(url));
            return driver.getCurrentUrl();

        } catch (TimeoutException e) {
            return null;
        }

    }

    // wait for frame:
    public void waitForFrameAndSwitchToIt(By frameLocator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    public void waitForFrameAndSwitchToIt(String frameNameOrID, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrID));
    }

    public void waitForFrameAndSwitchToIt(int frameIndex, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    }

    public void waitForFrameAndSwitchToIt(WebElement frameElement, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
    }

    // wait for windows:

    public boolean waitForWindow(int expectedNumberOfWindows, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

        try {
            return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows));
        } catch (Exception e) {
            System.out.println("expectedNumberOfWindows are not correct");
            return false;
        }

    }

    public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
                .pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class).withMessage("===Element is not found====");

        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
                .pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class).withMessage("===Element is not found====");

        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public boolean isPageLoaded(int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"))
                .toString();
        return Boolean.parseBoolean(flag);// true
    }

    @Step("Waiting for elements using: {0} and timeout: {1}")
    public WebElement waitForElementVisible(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element;
    }

    public List<WebElement> waitForAllElementsVisible(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

        try{
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        }catch(TimeoutException e){
            return Collections.EMPTY_LIST;
        }
    }

    public void clickWhenReady(By locator, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

}