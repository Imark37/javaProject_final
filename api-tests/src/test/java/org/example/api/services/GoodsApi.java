package org.example.api.services;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.api.base.BasicApi;

public class GoodsApi extends BasicApi {

    @Step("Создать товар: {name}")
    public Response addGood(String name, double price) {
        return request()
                .body("{\"name\": \"" + name + "\", \"price\": " + price + "}")
                .when()
                .post("/goods/add");
    }

    @Step("Получить товар по id: {id}")
    public Response getGood(int id) {
        return request().when().get("/goods/" + id);
    }

    @Step("Получить список товаров")
    public Response getGoodsList() {
        return request().when().get("/goods/list");
    }

    @Step("Получить список товаров (size = {size})")
    public Response getGoodsListWithSize(int size) {
        return request()
                .queryParam("size", size)
                .when()
                .get("/goods/list");
    }

    @Step("Удалить товар по id: {id}")
    public Response deleteGood(int id) {
        return request().when().delete("/goods/" + id);
    }

    @Step("Обновить товар по id: {id}")
    public Response patchGood(int id, String name, double price) {
        return request()
                .body("{\"name\": \"" + name + "\", \"price\": " + price + "}")
                .when()
                .patch("/goods/" + id);
    }
}