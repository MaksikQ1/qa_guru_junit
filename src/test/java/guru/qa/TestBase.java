package guru.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {

    @BeforeAll
    static void setupConfig() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
    }

    @BeforeEach
    void openBrowser() {
        Selenide.open("/automation-practice-form");
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }
}