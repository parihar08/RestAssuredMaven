package files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class Utilities {

	public static XmlPath rawToXML(Response res) {

		String responseString = res.asString(); // converts raw format to String
		System.out.println("Raw string response for the create request: " + responseString);
		XmlPath xp = new XmlPath(responseString); // converts the string into xml object
		return xp;
	}

	public static JsonPath rawToJSON(Response res) {

		String responseString = res.asString(); // converts raw format to String
		System.out.println("Raw string response for the create request: " + responseString);
		JsonPath js = new JsonPath(responseString); // converts the string into json object
		return js;
	}
	
	//Method to generate string from JSON or XML xpath/file
	public static String generateStringFromResource(String path) throws IOException{
		
		return new String(Files.readAllBytes(Paths.get(path)));
	}
			
	
	
}
