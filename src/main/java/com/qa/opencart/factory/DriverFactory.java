package com.qa.opencart.factory;

import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.utils.LogUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DriverFactory {

    public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<>();
    OptionsManager optionsManager;
    Properties prop;
    //public static Logger log = LogManager.getLogger(DriverFactory.class);

    /**
     * This method is used to initialize the driver on the basis of given
     * browser name.
     * @Param Properties
     */
    public WebDriver initDriver(Properties properties){
    LogUtil.info("Properties: "+prop);
    String browserName = properties.getProperty("browser");
    LogUtil.info("Browser Name :"+browserName);
    optionsManager = new OptionsManager(properties);
    switch (browserName.toLowerCase().trim()){
        case "chrome":
            tldriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
            break;
        case "edge":
            tldriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
            break;
        case "firefox":
            tldriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
            break;
        case "safari":
            tldriver.set(new SafariDriver());
            break;
        default:
            System.out.println("Plz pass the Valid Browser Name: "+browserName);
            LogUtil.error("Please pass the Valid Browser Name :"+browserName);
            throw new BrowserException("=====INVALID BROWSER=====");
        }
        getDriver().get(properties.getProperty("url"));
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        return getDriver();
    }

    public static WebDriver getDriver(){
        return tldriver.get();
    }

    /**
     * This is used to Initialize Properties
     * @return
     */
    //mvn clean install -Denv="stage"
    public Properties initProp(){
        String envName = System.getProperty("env");
        FileInputStream ip = null;

        if(envName==null){
            //System.out.println("Env is Null... so we are going to go with QA....");
            LogUtil.warn("Env is null, hence running the tests on QA env by default...");
            try {
                ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        else {
            LogUtil.info("------Proceeding with the Given Region------");
            switch (envName.toLowerCase().trim()){
                case "qa":
                    try {
                        ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "stage":
                    try {
                        ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "dev":
                    try {
                        ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    LogUtil.error("-----Invalid Env Name-----"+envName);
                    throw new FrameworkException("====INVALID ENV NAME===  "+envName);
            }

        }

        prop = new Properties();
        try {
            prop.load(ip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return prop;
    }

    public static File getScreenShotFile(){
        return ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
    }

    public static byte[] getScreenShotByte(){
        return ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    public static String getScreenShotBase64(){
        return ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BASE64);
    }


}
