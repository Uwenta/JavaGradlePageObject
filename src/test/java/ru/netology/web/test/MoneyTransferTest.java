package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV1;
import ru.netology.web.page.LoginPageV2;

import static com.codeborne.selenide.Selenide.open;


class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        //Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
        // тест переводит случайную валидную сумму с одной карты на другую, а потом обратно ту же самую сумму.
    void shouldTransferMoneyBetweenOwnCardsV1() {
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var ListCardsPage = verificationPage.validVerify(verificationCode);

        int balance1 = ListCardsPage.getCardBalance(1);
        int amount = ListCardsPage.getValidTransferAmount(2);
        var DashboardPage = ListCardsPage.topUpButton(1);
        ListCardsPage = DashboardPage.topUp(amount, 2);

        int expected = balance1 + amount;
        int actual = ListCardsPage.getCardBalance(1);

        int balance2 = ListCardsPage.getCardBalance(2);
        DashboardPage = ListCardsPage.topUpButton(2);
        ListCardsPage = DashboardPage.topUp(amount, 1);

        int expected2 = balance2 + amount;
        int actual2 = ListCardsPage.getCardBalance(2);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected2, actual2);

    }

    @Test
        // сумма перевода равна сумме баланса карты, с которой осуществляется перевод
    void shouldNotTransferValidAmountEqualToBalance() {
        var loginPage = new LoginPageV2();
//    var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var ListCardsPage = verificationPage.validVerify(verificationCode);
        int balance1 = ListCardsPage.getCardBalance(1);
        int amount = ListCardsPage.getCardBalance(2);
        var DashboardPage = ListCardsPage.topUpButton(1);
        ListCardsPage = DashboardPage.topUp(amount, 2);

        int expected = balance1 + amount;
        int actual = ListCardsPage.getCardBalance(1);

        Assertions.assertEquals(expected, actual);

        int balance2 = ListCardsPage.getCardBalance(2);
        DashboardPage = ListCardsPage.topUpButton(2);
        ListCardsPage = DashboardPage.topUp(amount, 1);

        int expected2 = balance2 + amount;
        int actual2 = ListCardsPage.getCardBalance(2);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected2, actual2);
    }

    @Test
        //Сумма перевода равна 0
    void shouldNotTransferValidAmountEqualToNull() {
        var loginPage = new LoginPageV2();
//    var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var ListCardsPage = verificationPage.validVerify(verificationCode);
        int amount = 0;
        var DashboardPage = ListCardsPage.topUpButton(1);

        Assertions.assertSame(DashboardPage, DashboardPage.topUpInvalid(amount, 2));

    }


    @Test
        //сумма перевода превышает баланс карты, с которой осуществляется перевод
    void shouldNotTransferInvalidAmountMoreBalance() {
        var loginPage = new LoginPageV2();
//      var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var ListCardsPage = verificationPage.validVerify(verificationCode);
        int amount = ListCardsPage.getInvalidTransferAmount(2);
        var DashboardPage = ListCardsPage.topUpButton(1);


        Assertions.assertSame(DashboardPage, DashboardPage.topUpInvalid(amount, 2));
    }

}

