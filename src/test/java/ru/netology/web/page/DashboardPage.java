package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement amount = $("[data-test-id='amount'] input");
    private SelenideElement from = $("[data-test-id='from'] input");
    private SelenideElement buttonTopUp = $("[data-test-id='action-transfer']");

    public DashboardPage() {
        SelenideElement heading = $("[data-test-id=dashboard]");
        heading.shouldBe(visible);
        amount.shouldBe(visible);
        from.shouldBe(visible);
    }

    public ListCardsPage topUp(int validAmount, int id) {
        amount.sendKeys(Keys.CONTROL + "a");
        amount.sendKeys(Keys.DELETE);
        amount.setValue(String.valueOf(validAmount));
        from.sendKeys(Keys.CONTROL + "a");
        from.sendKeys(Keys.DELETE);
        from.setValue(DataHelper.getNumberCard(id));
        buttonTopUp.click();
        return new ListCardsPage();
    }

    public DashboardPage topUpInvalid(int invalidAmount, int id) {
        amount.sendKeys(Keys.CONTROL + "a");
        amount.sendKeys(Keys.DELETE);
        amount.setValue(String.valueOf(invalidAmount));
        from.sendKeys(Keys.CONTROL + "a");
        from.sendKeys(Keys.DELETE);
        from.setValue(DataHelper.getNumberCard(id));
        buttonTopUp.click();
        return new DashboardPage();
    }


}
