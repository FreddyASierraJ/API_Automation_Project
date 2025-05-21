package utils;

import io.restassured.path.json.JsonPath;

import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtils {
    public static String readJsonAsString(String filepath){

        try{
            return new String(Files.readAllBytes(Paths.get(filepath)));
        }catch (Exception e){
            throw new RuntimeException("Could not read JSON file: "+filepath, e);
        }
    }
    public static JsonPath readJsonAsJsonPath(String filepath){
        return new JsonPath(readJsonAsString(filepath));
    }
}
