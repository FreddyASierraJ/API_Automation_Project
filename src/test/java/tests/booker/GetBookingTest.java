package tests.booker;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetBookingTest {
    @Test
    void getBooking() {
        Response response =given().given().contentType(ContentType.JSON).when().log().all().get("https://restful-booker.herokuapp.com/booking/1");
        System.out.println(response.asString());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200,response.statusCode(),"Status code was not 200");

    }
}
