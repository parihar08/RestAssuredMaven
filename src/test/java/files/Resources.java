package files;


import java.io.IOException;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Resources {
	
	
	public static String placePostData(){
		
		String resource = "/maps/api/place/add/json";
		return resource;
	}
	
	public static String getJiraSessionKey() throws IOException {
		
		RestAssured.baseURI = "http://localhost:8080";
		// Create the session
		Response res = RestAssured.given().headers("Content-Type", "application/json")
				.body("{ \"username\": \"sparihar08\", \"password\": \"parihar_08\" }").
				when().post("rest/auth/1/session").then().assertThat().statusCode(200).
				extract().response(); //returns raw format in the res object
		
		JsonPath js = Utilities.rawToJSON(res); //Using rawToJson method to convert raw string to JSON object
		String sessionId = js.get("session.value");
		System.out.println("Session ID generated is: "+sessionId);
		return sessionId;

	}
}
