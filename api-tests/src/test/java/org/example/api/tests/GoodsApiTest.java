package org.example.api.tests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.api.asserts.ApiAssert;
import org.example.api.services.GoodsApi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class GoodsApiTest {

    private final GoodsApi goodsApi = new GoodsApi();
    private final List<Integer> createdIds = new ArrayList<>();

    @AfterEach
    void cleanUp() {
        for (int id : createdIds) {
            goodsApi.deleteGood(id);
        }
        createdIds.clear();
    }

    @Test
    void testAddGood_200() {
        Response response = goodsApi.addGood("Сыр_" + System.currentTimeMillis(), 99.0);
        ApiAssert.assertThat(response).statusCodeIs(200).hasDataId();

        int id = response.jsonPath().getInt("data.id");
        createdIds.add(id);
    }

    @Test
    void testGetGood_200() {
        int id = createGoodAndGetId("Молоко_" + System.currentTimeMillis());
        Response response = goodsApi.getGood(id);
        ApiAssert.assertThat(response).statusCodeIs(200);
    }

    @Test
    void testGetGood_404() {
        Response response = goodsApi.getGood(999999);
        ApiAssert.assertThat(response).statusCodeIs(404);
    }

    @Test
    void testGetGoodsList_200() {
        Response response = goodsApi.getGoodsList();
        ApiAssert.assertThat(response).statusCodeIs(200);
    }

    @Test
    void testDeleteGood_200() {
        int id = createGoodAndGetId("Удалить_" + System.currentTimeMillis());
        Response response = goodsApi.deleteGood(id);
        ApiAssert.assertThat(response).statusCodeIs(200);
        createdIds.remove(Integer.valueOf(id));
    }

    @Test
    void testDeleteGood_404() {
        Response response = goodsApi.deleteGood(999999);
        ApiAssert.assertThat(response).statusCodeIs(404);
    }

    @Test
    void testPatchGood_200() {
        int id = createGoodAndGetId("Старое_" + System.currentTimeMillis());
        Response response = goodsApi.patchGood(id, "Новое_" + System.currentTimeMillis(), 150.0);
        ApiAssert.assertThat(response).statusCodeIs(200);
    }

    @Test
    void testPatchGood_404() {
        Response response = goodsApi.patchGood(999999, "Новое", 150.0);
        ApiAssert.assertThat(response).statusCodeIs(404);
    }

    @Test
    void testAddGoodAppearsInList() {
        String name = "Товар_" + System.currentTimeMillis();
        createGoodAndGetId(name);

        Response response = goodsApi.getGoodsListWithSize(100);
        ApiAssert.assertThat(response).statusCodeIs(200);

        List<String> names = response.jsonPath().getList("goods.name");
        org.assertj.core.api.Assertions.assertThat(names)
                .as("Список должен содержать созданный товар")
                .contains(name);
    }

    @Step("Создать товар и получить его id")
    private int createGoodAndGetId(String name) {
        Response response = goodsApi.addGood(name, 99.0);
        int id = response.jsonPath().getInt("data.id");
        createdIds.add(id);
        return id;
    }
}