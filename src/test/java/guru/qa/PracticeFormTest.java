package guru.qa;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class PracticeFormTest extends TestBase {

    @ParameterizedTest(name = "CSV: Заполнение формы для {0} {1}")
    @CsvFileSource(resources = "/user.data.csv", numLinesToSkip = 1)
    void fillFormWithCsvData(String firstName, String lastName, String email,
                             String phone, String address) {
        fillAndCheckForm(firstName, lastName, email, phone, address);
    }

    @ParameterizedTest(name = "Method: Комбинация {0}")
    @MethodSource("userCombinationsProvider")
    void fillFormWithDifferentCombinations(String fullName, String contact, String location) {
        String[] nameParts = fullName.split(" ");
        String[] contactParts = contact.split(",");
        fillAndCheckForm(
                nameParts[0],
                nameParts.length > 1 ? nameParts[1] : "",
                contactParts[1],
                contactParts[0],
                location
        );
    }

    @ParameterizedTest(name = "Value: Проверка телефона {0}")
    @ValueSource(strings = {"9999999999", "7777777777", "6666666666"})
    void phoneFieldShouldAcceptDifferentValues(String phone) {
        $("#userNumber").setValue(phone).shouldHave(value(phone));
    }

    private void fillAndCheckForm(String firstName, String lastName, String email,
                                  String phone, String address) {
        $("#firstName").setValue(firstName).shouldHave(value(firstName));
        $("#lastName").setValue(lastName).shouldHave(value(lastName));
        $("#userEmail").setValue(email).shouldHave(value(email));
        $("#userNumber").setValue(phone).shouldHave(value(phone));
        $("#currentAddress").setValue(address).shouldHave(value(address));
    }

    static Stream<Arguments> userCombinationsProvider() {
        return Stream.of(
                arguments("Maks Familiya", "9994446633,maksik@gmail.com", "Street 15"),
                arguments("Maks LastName", "9994446633,maksik@yandex.com", "Street 1")
        );
    }
}