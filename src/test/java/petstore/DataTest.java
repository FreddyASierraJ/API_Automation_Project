package petstore;

import api.petstore.PetApi;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.JsonUtils;

import javax.lang.model.element.NestingKind;

public class DataTest {
    private static final Logger log= LoggerFactory.getLogger(DataTest.class);

    @Test
    void testReadingJsonFromFile() {

        String path ="src/test/resources/data/user_profile.json";
        JsonPath json = JsonUtils.readJsonAsJsonPath(path);

        log.info("JSON Content : \n"+json.prettyPrint());


        //Datos simples

        String expectedName = "Karine Ramos";
        String actualName = json.getString("user.name");
        Assertions.assertEquals(expectedName,actualName,"Name is incorrect");

        boolean expectedActive = true;
        boolean actualActive =json.getBoolean("user.active");
        Assertions.assertEquals(expectedName,actualName,"Active field is incorrect");

        //obtener datos anidados

        String expectedCity ="Miami";
        String actualCity =json.getString("user.address.city");
        Assertions.assertEquals(expectedCity,actualCity,"City is incorrect");

        double expectedLat =25.7617;
        double actualLat =json.getDouble("user.address.coordinates.lat");
        log.info("expectedLat: "+expectedLat +" | "+"actual lat:"+actualLat);
        Assertions.assertEquals(expectedLat,actualLat,"Expected lat is not correct");

        int expectedFirstOrderId= 5001;
        int actualFirstObjectId =json.getInt("user.orders[0].orderId");
        log.info("ExpetedFirstOrderId: "+expectedFirstOrderId+" | ActualFirstOrderId "+actualFirstObjectId);
        Assertions.assertEquals(expectedFirstOrderId,actualFirstObjectId,"First OrderId is incorrect");

        String expected_2ndOrderItem = "Notebook";
        String actual_2ndOrderItem = json.getString("user.orders[1].items[0].name");
        log.info("expected_2ndOrderItem "+expected_2ndOrderItem+" | actual_2ndOrderItem "+actual_2ndOrderItem);
        Assertions.assertEquals(expected_2ndOrderItem,actual_2ndOrderItem,"2nd order item name is incorrect");

        int expectedTotalOrder1=7;
        int actualItemQty = json.getInt("user.orders[0].items[0].quantity");
        int actualItemQty2 = json.getInt("user.orders[0].items[1].quantity");
        log.info("actualItemQty: "+actualItemQty +"| actualItemQty2 "+actualItemQty2);
        int actualTotalOrder1 = actualItemQty+actualItemQty2;
        log.info("ActualTotalOrder 1 : "+actualTotalOrder1);

        Assertions.assertEquals(expectedTotalOrder1,actualTotalOrder1, "Total Order1 is incorrect");

        String expectdStatusOrder1 ="delivered";
        String currentStatusOrder1 = json.getString("user.orders[0].status");
        log.info("expectdStatusOrder1 "+expectdStatusOrder1+"| currentStatusOrder1"+currentStatusOrder1);
        Assertions.assertEquals(expectdStatusOrder1,currentStatusOrder1,"Order 1 status is incorrect");

        String expectedStatgusOrder2 ="pending";
        String currentStatusOrder2 = json.getString("user.orders[1].status");
        log.info("expectdStatusOrder2 "+expectedStatgusOrder2+"| currentStatusOrder1"+currentStatusOrder2);
        Assertions.assertEquals(expectedStatgusOrder2,currentStatusOrder2,"Order 2 status is incorrect");




    }

}
