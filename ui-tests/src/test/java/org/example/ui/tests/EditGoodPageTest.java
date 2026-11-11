package org.example.ui.tests;

import org.example.common.api.RestApiBuilder;
import org.example.common.config.ConfigProvider;
import org.example.ui.page.AdminPage;
import org.example.ui.page.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

class EditGoodPageTest extends BaseTest {

    private final List<Integer> createdIds = new ArrayList<>();
    private final AdminPage adminPage = new AdminPage();

    @AfterEach
    void cleanUp() {
        for (int id : createdIds) {
            given().spec(RestApiBuilder.getBuilder()).delete("/goods/" + id);
        }
        createdIds.clear();
    }

    @Test
    void editGoodAndCheckOnShop() {
        String ts = String.valueOf(System.currentTimeMillis());
        String oldName = "СтароеИмя_" + ts;
        String newName = "НовоеИмя_" + ts;

        // 1. Создаём товар через API (подготовка данных)
        int id = given().spec(RestApiBuilder.getBuilder())
                .body("{\"name\": \"" + oldName + "\", \"price\": 100.0}")
                .post("/goods/add").jsonPath().getInt("data.id");
        createdIds.add(id);

        // 2. Логинимся в админку (через конфиг)
        open("/login");
        loginPage.setUsername(ConfigProvider.CONFIG.adminUsername())
                .setPassword(ConfigProvider.CONFIG.adminPassword())
                .clickSignIn();

        // 3. Редактируем через UI
        adminPage.editName(String.valueOf(id), newName)
                .clickSave(String.valueOf(id));

        // 4. Проверяем уведомление
        adminPage.check().toastContains("обновлен");

        // 5. Идём на витрину и проверяем новые данные
        open("/");
        mainPage.check().productNameContains(newName);
    }
}