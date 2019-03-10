package RestAssuredMaven.RestAssuredMaven;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import files.Utilities;

public class GetJSONTraveseTest {
	
	@Test()
	public void Test1() {
		//Provide Base url or host
		RestAssured.baseURI = "https://maps.googleapis.com";
		
		//In Given block we need to pass components - request headers, request parameters, request cookies, body
		Response res = RestAssured.
		given().
			log().all().  //logs all request specifications including params, body, headers, cookies, method, path
			param("location", "-33.8688197").
			param("radius",500).
			param("key","daacav").
			header("key","value").
			cookie("key","value").
			body("Body content").
		
		//In When block we need to pass resource information - get(resource)  post(resource)  put(resource)
		when().
			get("/maps/api/place/nearbysearch/json").   // get(resource)
		
		//In Then block we need to do assertions to validate the response body and response headers
		then().
			log().all().  //logs all response specifications including body, headers, status code
			assertThat().
			statusCode(200).
			and().contentType(ContentType.JSON). //validate if content type response header is JSON or XML
			and().body("results[0].geometry.location.lat",equalTo("-33.8688197")). //validates this number is present in response body
			and().body("results[0].name", equalTo("Sydney")). //validates this name is present in response body
			and().body("results[0].place_id", equalTo("241241241")). //validates this place id is present in response body
			and().header("Server", "pablo"). //validates Server header with name pablo is present in response header
		
		//In Extract block we can pull out the body response in to some variable for future usage
		extract().
			response();
		
			JsonPath js = Utilities.rawToJSON(res);
			int count = js.get("results.size()");  // to find out the size of the results array in json response body
			System.out.println(count);
			for(int i=0;i<count;i++){
				String name = js.get("results["+i+"].name"); // to find all the names in the response json
				System.out.println(name);
			}
	}
	

}
