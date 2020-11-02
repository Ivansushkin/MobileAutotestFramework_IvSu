package moblie;

import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class AppiumSetup {
    AppiumDriver<MobileElement> driver;

    String APP_PACKAGE = "ru.kinopoisk.dev";
    String APP_MAIN_ACTIVITY = "ru.kinopoisk.presentation.screen.tabs.RedirectTabsIntentsActivity";
    String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    /*
    Создаем синглтон:
    Теперь вместо new AppiumSetup() юзаем AppiumSetup.getInstance()
    Теперь экземпляр класса не будет каждый раз создаваться заново, а будет использовать старый если он есть
    */
    private static AppiumSetup instance;
    private AppiumSetup(){}
    public static AppiumSetup getInstance(){
        if(instance == null){
            instance = new AppiumSetup();
        }
        return instance;
    }


    public void setAndroidDriver(String pathToApp) throws MalformedURLException {
        URL appiumServer = new URL(APPIUM_URL);

        DesiredCapabilities androidCapabilities = new DesiredCapabilities();
        androidCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator1");
        androidCapabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, "1.15.1");
        androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10");
        androidCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        androidCapabilities.setCapability("appPackage", APP_PACKAGE);
        androidCapabilities.setCapability("appActivity", APP_MAIN_ACTIVITY);
        androidCapabilities.setCapability(MobileCapabilityType.APP, pathToApp);

        driver = new AndroidDriver(appiumServer, androidCapabilities);
        WebDriverRunner.setWebDriver(driver);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    public void quitDriver(){
        driver.quit();
    }
}