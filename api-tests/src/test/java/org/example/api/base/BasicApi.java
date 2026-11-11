package org.example.api.base;

import io.restassured.specification.RequestSpecification;
import org.example.common.api.RestApiBuilder;

import static io.restassured.RestAssured.given;

public abstract class BasicApi {

    protected RequestSpecification request() {
        return given().spec(RestApiBuilder.getBuilder());
    }
}