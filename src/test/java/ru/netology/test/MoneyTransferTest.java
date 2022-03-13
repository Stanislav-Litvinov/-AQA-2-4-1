package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashbordPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;

class MoneyTransferTest {
    int sum;
    static DashbordPage dashbordPage;
    int begBalance1;
    int begBalance2;
    static int endBalance1;
    static int endBalance2;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
//        begBalance1 = dashboardPage.getBalance(dashboardPage.card1);
//        begBalance2 = dashboardPage.getBalance(dashboardPage.card2);
        begBalance1 = dashbordPage.getFirstCardBalance();
        begBalance2 = dashbordPage.getSecondCardBalance();
    }

    @AfterEach
    void getBalance() {
        endBalance1 = dashbordPage.getFirstCardBalance();
        endBalance2 = dashbordPage.getSecondCardBalance();
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        sum = 100;
        $$("[data-test-id='action-deposit']").first().click();
        $("[data-test-id='amount'] input").val(String.valueOf(sum));
        $("[data-test-id='from'] input").val(DataHelper.getSecondCard().getNumber());
        $("[data-test-id='action-transfer']").click();
        $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']").shouldHave(Condition.text("10100"));

    }
}