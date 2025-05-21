package petstore;

import api.petstore.PetApi;
import io.restassured.response.Response;
import listeners.ExtentReportExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;


@ExtendWith(ExtentReportExtension.class)

public class petStoreTest {
    private static final Logger log= LoggerFactory.getLogger(petStoreTest.class);

    static PetApi petApi;

    @BeforeAll
    static void beforeAll() {
        petApi=new PetApi();

    }

    @Test
    void uploadPetImage() {

        File file = new File("src/test/resources/file/image.png");
        Response response = petApi.uploadImage(1234, file);
        log.info(response.asString());

        Assertions.assertEquals(200, response.statusCode(),"Status code is not 200");

        String message = response.jsonPath().getString("message");

        log.info("message ->"+message);

        Assertions.assertTrue(message.contains("image.png"),"Message does not contains the image name");
    }

    @ParameterizedTest
    @CsvSource({"1010, Luna, available",
                "1011, Bella, sold",
                "1012, Cruela, available"})
    void testAddingMultiplePets(int petId, String name, String status) {
        Response response= petApi.addPet(petId,name,status);
        Assertions.assertEquals(200, response.statusCode(),"Status code was not 200");

        int currentPetId=response.jsonPath().getInt("id");
        Assertions.assertEquals(petId,currentPetId,"Current petId is not correct");

        String currentName = response.jsonPath().getString("name");
        Assertions.assertEquals(name,currentName,"Current Name is not correct");

        String currentStatus= response.jsonPath().getString("status");
        Assertions.assertEquals(status,currentStatus,"Current status is not correct");


    }

    @Test
    void testWillFail() {
        fail("This is to show a test failing in the report");
    }
}
