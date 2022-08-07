import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    public String inputDate(int day) {
        return LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldCorrectFillingForm() {

        String meetingDate = inputDate(7);

        open("http://localhost:9999/");
        $("[data-test-id=\"city\"] input").setValue("Волгоград");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(meetingDate);
        $("[data-test-id=\"name\"] input").setValue("Петров-Водкин Евгений");
        $("[data-test-id=\"phone\"] input").setValue("+79645813332");
        $("[data-test-id=\"agreement\"]").click();
        $x("//span[@class='button__text']").click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + meetingDate)).shouldBe(Condition.visible, Duration.ofSeconds(15));

    }
}
