package org.example.ui.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.assertj.core.api.AbstractAssert;

public class AdminPageAssert extends AbstractAssert<AdminPageAssert, AdminPage> {

    public AdminPageAssert(AdminPage actual) {
        super(actual, AdminPageAssert.class);
    }

    public AdminPageAssert toastContains(String text) {
        Selenide.$$(".toast")
                .findBy(Condition.text(text))
                .shouldBe(Condition.visible);
        return this;
    }

    public AdminPageAssert tableContains(String text) {
        actual.goodsTable.shouldHave(Condition.text(text));
        return this;
    }
}