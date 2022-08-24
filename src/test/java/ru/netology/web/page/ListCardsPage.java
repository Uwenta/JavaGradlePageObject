package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Value;
import lombok.val;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ListCardsPage {

    private SelenideElement firstCard = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private SelenideElement secondCard = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");
    private SelenideElement buttonFirstCard = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] button");
    private SelenideElement buttonSecondCard = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] button");


    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public ListCardsPage() {
        firstCard.shouldBe(visible);
        secondCard.shouldBe(visible);
    }

    public DashboardPage topUpButton(int id) {
        if (id == 1) {
            buttonFirstCard.click();
        } else buttonSecondCard.click();

        return new DashboardPage();
    }


    public int getValidTransferAmount(int id) {
        int balance = getCardBalance(id);

        return (int) (Math.random() * balance);
    }

    public int getInvalidTransferAmount(int id) {
        int balance = getCardBalance(id);

        return (int) ((Math.random() * 1_000_000) + balance);
    }


    public int getCardBalance(int id) {
        String text = null;
        if (id == 1) {
            text = firstCard.text();
        }
        if (id == 2) {
            text = secondCard.text();
        }
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }


}
