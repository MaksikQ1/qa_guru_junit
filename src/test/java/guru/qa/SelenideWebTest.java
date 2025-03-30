package guru.qa;

import guru.qa.data.Language;
import guru.qa.data.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideWebTest {

  @EnumSource(Language.class)
  @ParameterizedTest
  void selenideSiteShouldDisplayCorrectText(Language language) {
    open("https://ru.selenide.org/");
    $$("#languages a").find(text(language.name())).click();
    $("h3").shouldHave(text(language.description));
  }

  static Stream<Arguments> selenideSiteShouldDisplayCorrectButtons() {
    return Stream.of(
        Arguments.of(
            Language.EN,
            List.of("Quick start", "Docs", "FAQ", "Blog", "Javadoc", "Users", "Quotes"),
            new Person("Dima", 34)
        ),
        Arguments.of(
            Language.RU,
            List.of("С чего начать?", "Док", "ЧАВО", "Блог", "Javadoc", "Пользователи", "Отзывы"),
            new Person("Valentin", 35)
        )
    );
  }

  @MethodSource
  @ParameterizedTest
  void selenideSiteShouldDisplayCorrectButtons(Language language, List<String> expectedButtons, Person person) {
    open("https://ru.selenide.org/");
    $$("#languages a").find(text(language.name())).click();
    $$(".main-menu-pages a").filter(visible)
            .shouldHave(texts(expectedButtons));
  }
}
