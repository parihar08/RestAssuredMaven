package RestAssuredMaven.RestAssuredMaven;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

public class PostCreatePlaceTest {

	@Test()
	public void Test1() {
		// Provide Base url or host
		RestAssured.baseURI = "http://216.10.245.166";

		// Different types of Parameters
		// 1. Path Parameter - is used to match a part of the url as a parameter(when there is no ? in the url)
		// 2. Query Parameter - is used to access key/value pairs in the query string of the url(the part after ?)
		// 3. Header Parameter

		// In Given block we need to pass components - request headers, request parameters, request cookies, body
		RestAssured.given().
			queryParam("key", "qaclick123").body("{" +

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

				"}").

		// In When block we need to pass resource information - get(resource) post(resource) put(resource)
				when().
					post("/maps/api/place/add/json").

		// In Then block we need to do assertions to validate the response body and response headers
				then().
					assertThat().statusCode(200).
					
					and().contentType(ContentType.JSON).
					
					and().body("status", equalTo("OK"));																																														

		// In Extract block we can pull out the body response in to some variable for future usage
	}

}
