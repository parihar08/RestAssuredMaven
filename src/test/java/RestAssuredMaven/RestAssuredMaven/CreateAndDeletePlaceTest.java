package RestAssuredMaven.RestAssuredMaven;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

public class CreateAndDeletePlaceTest {

	@Test()
	public void Test1() {
		// Provide Base url or host
		RestAssured.baseURI = "http://216.10.245.166";
		String bodyContent = "{" +

				"\"location\":{" +

				"\"lat\" : -38.383494," +

				"\"lng\" : 33.427362 " +

				"}," +

				"\"accuracy\":50," +

				"\"name\":\"Frontline house\"," +

				"\"phone_number\":\"(+91) 983 893 3937\"," +

				" \"address\" : \"29, side layout, cohen 09\"," +

				"\"types\": [\"shoe park\",\"shop\"]," +

				" \"website\" : \"http://google.com\"," +

				" \"language\" : \"French-IN\" " +

				"}";

		// Different types of Parameters
		// 1. Path Parameter - is used to match a part of the url as a parameter(when there is no ? in the url)
		// 2. Query Parameter - is used to access key/value pairs in the query string of the url(the part after ?)
		// 3. Header Parameter

		// In Given block we need to pass components - request headers, request parameters, request cookies, body
		Response res = RestAssured.given().
			queryParam("key", "qaclick123").
			body(bodyContent).

		// In When block we need to pass resource information - get(resource) post(resource) put(resource)
				when().
					post("/maps/api/place/add/json").

		// In Then block we need to do assertions to validate the response body and response headers
				then().
					assertThat().statusCode(200).
					
					and().contentType(ContentType.JSON).
					
					and().body("status", equalTo("OK")).
		
		// Create place > returns response place_id

		// In Extract block we can pull out the body response in to some variable for future usage
			extract().response(); //returns raw format in the res object
			String responseString = res.asString(); // converts raw format to String
			System.out.println("Raw string response for the create request: "+ responseString);
			
		//Convert the String in to json format to traverse through
			JsonPath js = new JsonPath(responseString); //converts the string into json object
			String placeid = js.get("place_id"); //pass the string path from create response which we want to access and returns us the value
			System.out.println("Place id: "+placeid +" converted to JSON object succcessfully");
			
		// Delete place > Now send this placeid in the delete request body
			RestAssured.given().
				queryParam("key", "qaclick123").
				body("{"+
				"\"place_id\" : \""+placeid+"\" "+  // placeid sent in the delete request body
						"}").         
			when().
				post("/maps/api/place/delete/json").
			then().
				assertThat().statusCode(200).
				and().contentType(ContentType.JSON).
				and().body("status", equalTo("OK"));
			System.out.println("Place id: "+placeid +" deleted succcessfully");
			
	}

}
