package org.example.ui.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class AdminPage {

    public SelenideElement nameInput = $("#n-name");
    public SelenideElement priceInput = $("#n-price");
    public SelenideElement createButton = $("#add-btn");
    public SelenideElement goodsTable = $("#tbody");

    public AdminPage fillName(String name) {
        nameInput.setValue(name);
        return this;
    }

    public AdminPage fillPrice(String price) {
        priceInput.setValue(price);
        return this;
    }

    public AdminPage clickCreate() {
        createButton.click();
        return this;
    }

    public AdminPage editName(String id, String newName) {
        $("#nm-" + id).shouldBe(com.codeborne.selenide.Condition.visible).clear();
        $("#nm-" + id).setValue(newName);
        return this;
    }

    public AdminPage clickSave(String id) {
        // Ищем кнопку сохранения внутри строки, где находится наш input
        $("#nm-" + id)
                .closest("tr")
                .$("[data-action='update']")
                .click();
        return this;
    }
    public AdminPageAssert check() {
        return new AdminPageAssert(this);
    }
}