package org.example.api.asserts;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.assertj.core.api.AbstractAssert;

public class ApiAssert extends AbstractAssert<ApiAssert, Response> {

    public ApiAssert(Response actual) {
        super(actual, ApiAssert.class);
    }

    public static ApiAssert assertThat(Response actual) {
        return new ApiAssert(actual);
    }

    @Step("Проверить, что код ответа = {expected}")
    public ApiAssert statusCodeIs(int expected) {
        if (actual.statusCode() != expected) {
            failWithMessage("Ожидался код %d, но получен %d", expected, actual.statusCode());
        }
        return this;
    }

    @Step("Проверить, что тело содержит поле 'data.id'")
    public ApiAssert hasDataId() {
        if (actual.jsonPath().get("data.id") == null) {
            failWithMessage("Поле 'data.id' отсутствует в ответе");
        }
        return this;
    }
}