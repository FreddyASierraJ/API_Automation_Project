package tests.booker;

import api.booker.BookingAPI;
import io.restassured.response.Response;
import listeners.ExtentReportExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Booking;

@ExtendWith(ExtentReportExtension.class)
@Tag("Booking_Regression")
public class BookingNegativeTests {
    private static final Logger log= LoggerFactory.getLogger(BookingNegativeTests.class);

    static BookingAPI bookingAPI;

    static  int bookingId;

    @BeforeAll
    static void beforeAll() {
        bookingAPI = new BookingAPI();

    }

    @Test
    void CreateBookingWithMissingValues() {

        Booking booking = new Booking(null,null,0,false,
                "","","");

        Response response=bookingAPI.createBooking(booking);

        Assertions.assertEquals(500,response.statusCode(),"Status code should be 500");
    }

    @Test
    void updateBookingWithInvalidToken() {

        Booking booking = new Booking("Teresa","Perez",150,true
                ,"2025-05-19","2025-05-23","Breakfast");

        Response response_createBooking = bookingAPI.createBooking(booking);
        Assertions.assertEquals(200,response_createBooking.statusCode(),"Status code is not 200");
        int bookingId = response_createBooking.jsonPath().getInt("bookingid");
        log.info("created bookingid "+bookingId);

        Response response = bookingAPI.updateBooking(bookingId,booking,"fake_token");
        Assertions.assertEquals(403,response.statusCode(),"Status code is not 403");

    }

    @Test
    void deleteNonExistingBooking() {
        Response response = bookingAPI.deleteBooking(457685);
        Assertions.assertEquals(405,response.statusCode(),"Status code is not 405");
    }
}
