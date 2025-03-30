package guru.qa;

import guru.qa.data.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class DuckDuckGoWebTest {

  @BeforeEach
  void setUp() {
    open("https://duckduckgo.com/");
  }

  @ValueSource(strings = {
      "Selenide", "JUnit 5", "Allure"
  })
  @ParameterizedTest(name = "Для поискового запроса {0} должен отдавать не пустой список карточек")
  @Tag("BLOCKER")
  void searchResultsShouldNotBeEmpty(String searchQuery) {
    $("#searchbox_input").setValue(searchQuery).pressEnter();
    $$("[data-testid='mainline'] li[data-layout='organic']")
        .shouldBe(sizeGreaterThan(0));
  }

  @CsvSource(value = {
      "Selenide , https://selenide.org",
      "JUnit 5 , https://junit.org"
  })
  @CsvFileSource(resources = "/test_data/searchResultsShouldContainExpectedUrl.csv")
  @ParameterizedTest(name = "Для поискового запроса {0} в первой карточке должна быть ссылка {1}")
  @Tag("BLOCKER")
  void searchResultsShouldContainExpectedUrl(String searchQuery, String expectedLink) {
    $("#searchbox_input").setValue(searchQuery).pressEnter();
    $("[data-testid='mainline'] li[data-layout='organic']")
        .shouldHave(text(expectedLink));
  }

  @Test
  @Tag("BLOCKER")
  @DisplayName("Для поискового запроса 'junit 5' должен отдавать не пустой список карточек")
  void successfulSearchJUnitTest() {
    $("#searchbox_input").setValue("junit 5").pressEnter();
    $$("[data-testid='mainline'] li[data-layout='organic']")
        .shouldBe(sizeGreaterThan(0));
  }

  @Test
  @Tag("BLOCKER")
  @DisplayName("Для поискового запроса 'selenide' должен показываться не пустой список фото")
  void successfulSearchPhotoTest() {
    $("#searchbox_input").setValue("selenide").pressEnter();
    $("[data-zci-link='images']").click();

    $$("img.tile--img__img")
        .shouldBe(sizeGreaterThan(0));
  }
}
