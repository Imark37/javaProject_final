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

class AddGoodNotificationPageTest extends BaseTest {

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
    void addGoodShowsNotification() {
        String goodName = "Товар_" + System.currentTimeMillis();

        // 1. Логинимся (берем из конфига)
        open("/login");
        loginPage.setUsername(ConfigProvider.CONFIG.adminUsername())
                .setPassword(ConfigProvider.CONFIG.adminPassword())
                .clickSignIn();

        // 2. Добавляем товар через UI (Админку)
        adminPage.fillName(goodName)
                .fillPrice("99")
                .clickCreate();

        // 3. Проверяем уведомление
        adminPage.check().toastContains("успешно добавлен");

        // 4. Находим id через API, чтобы потом удалить
        List<java.util.Map<String, Object>> goods = given().spec(RestApiBuilder.getBuilder())
                .get("/goods/list?size=100").jsonPath().getList("goods");
        int id = goods.stream()
                .filter(g -> goodName.equals(g.get("name")))
                .map(g -> (Integer) g.get("id"))
                .findFirst().orElseThrow();
        createdIds.add(id);
    }
}