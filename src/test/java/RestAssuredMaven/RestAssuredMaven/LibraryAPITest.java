package RestAssuredMaven.RestAssuredMaven;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

public class LibraryAPITest {

	@Test()
	public void TestAPI() {
		// Provide Base url or host
		RestAssured.baseURI = "http://216.10.245.166";
		String bodyContent = "{\r\n\"name\":\"Learn Appium Automation with Java\","
				+ "\r\n\"isbn\":\"bfcd\",\r\n\"aisle\":\"8834\",\r\n\"author\":\"Sandeep Parihar\"\r\n}";

		// Different types of Parameters
		// 1. Path Parameter - is used to match a part of the url as a parameter(when there is no ? in the url)
		// 2. Query Parameter - is used to access key/value pairs in the query string of the url(the part after ?)
		// 3. Header Parameter

		// In Given block we need to pass components - request headers, request parameters, request cookies, body
		Response res = RestAssured.given().
				headers("Content-Type","application/json").
			//queryParam("key", "qaclick123").
			body(bodyContent).

		// In When block we need to pass resource information - get(resource) post(resource) put(resource)
				when().
					post("/Library/Addbook.php").

		// In Then block we need to do assertions to validate the response body and response headers
				then().
					assertThat().statusCode(200).
					and().body("Msg", equalTo("successfully added")).
		
		// Create place > returns response place_id

		// In Extract block we can pull out the body response in to some variable for future usage
			extract().response(); //returns raw format in the res object
			String responseString = res.asString(); // converts raw format to String
			System.out.println("Raw string response for the create request: "+ responseString);
			
		//Convert the String in to json format to traverse through
			JsonPath js = new JsonPath(responseString); //converts the string into json object
			String ID = js.get("ID"); //pass the string path from create response which we want to access and returns us the value
			System.out.println("ID: "+ID +" converted to JSON object succcessfully");
			
		// Delete Book > Now send this ID in the delete request body
			RestAssured.given().
				//queryParam("key", "qaclick123").
				body("{"+
				"\"ID\" : \""+ID+"\" "+  // ID sent in the delete request body
						"}").         
			when().
				post("/Library/DeleteBook.php").
			then().
				assertThat().statusCode(200);
				//and().contentType(ContentType.JSON).
				//and().body("status", equalTo("OK"));
			System.out.println("ID: "+ID +" deleted succcessfully");
			
	}

}
