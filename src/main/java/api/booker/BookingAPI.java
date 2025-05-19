package api.booker;

import api.base.APIBase;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Booking;

import java.lang.module.ResolutionException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BookingAPI extends APIBase {

    private static final Logger log= LoggerFactory.getLogger(BookingAPI.class);

    private static final String BASE_URL ="https://restful-booker.herokuapp.com";
    private static final String AUTH_ENDPOINT ="/auth";
    private static final String BOOKING_ENDPOINT ="/Booking";

    private String token;
    public BookingAPI() {
        super(BASE_URL);
        token = getToken();
    }
    //METODO DE SOPORTE QUE SPORTA CUALQUIER VARIABLE DE AMBIENTE
    public static String getEnvVar(String key){
        String value = System.getenv(key);
        if(value == null || value.isEmpty()){
            throw new RuntimeException("Environment exception"+ key + "is not set");
        }
        return value;
    }
    private  String getUserName(){
        return getEnvVar("BOOKER_USER");
    }
    private  String getPassword(){
        return getEnvVar("BOOKER_PASSWORD");
    }
    public String getToken(){
        Map<String, Object> body = new HashMap<>();
        body.put("username",getUserName());
        body.put("password",getPassword());

        Response response = post_no_Logs(AUTH_ENDPOINT,body);

        return response.jsonPath().getString("token");

    }
    public Response getBooking(int bookingId){
        return get(BOOKING_ENDPOINT+"/"+bookingId);
    }
    public Response createBooking(Booking booking){
        Map<String,Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin",booking.getCheckin());
        bookingDates.put("checkout",booking.getCheckout());

        Map<String,Object> body = new HashMap<>();
        body.put("firstname",booking.getFirstname());
        body.put("lasttname",booking.getLastname());
        body.put("totalprice",booking.getTotalprice());
        body.put("depositpaid",booking.isDepositpaid());
        body.put("bookingdates",bookingDates);
        body.put("addtionalneeds",booking.getAdditionalneeds());


        return post(BOOKING_ENDPOINT,body);
    }
    public Response updateBooking(int bookingId, Booking booking){

        Map<String,Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin",booking.getCheckin());
        bookingDates.put("checkout",booking.getCheckout());

        Map<String,Object> body = new HashMap<>();
        body.put("firstname",booking.getFirstname());
        body.put("lasttname",booking.getLastname());
        body.put("totalprice",booking.getTotalprice());
        body.put("depositpaid",booking.isDepositpaid());
        body.put("bookingdates",bookingDates);
        body.put("addtionalneeds",booking.getAdditionalneeds());

        return put(BOOKING_ENDPOINT+"/"+bookingId,body,token);

    }
    public Response deleteBooking(int bookingId){
        return delete(BOOKING_ENDPOINT+"/"+bookingId,token);
    }
}
