package RestAssuredMaven.RestAssuredMaven;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

public class DataDrivenExcelHashMap{

	@Test()
	public void TestAPI() throws IOException {
		
		//Using excel utility to load data from ExcelSheet
		DataDrivenExcelUtility d=new DataDrivenExcelUtility();
		ArrayList<String> data = d.getData("RestAddBook","RestAssured");
		
		HashMap<String,Object> jsonAsMap = new HashMap<String, Object>();
		jsonAsMap.put("name", data.get(1));
		jsonAsMap.put("isbn", data.get(2));
		jsonAsMap.put("aisle", data.get(3));
		jsonAsMap.put("author", data.get(4));
		
		//How to treat nested json?
		//location parameter has value in the form of hashmap that has latitude and longitude values
		//Create another mini hashmap for nested data
		HashMap<String,Object> nestedJson = new HashMap<String, Object>();
		nestedJson.put("latitude", "123");
		nestedJson.put("longitude", "456");
		
		//Add the object of nested hashmap here
		jsonAsMap.put("location", nestedJson);
		
		// Provide Base url or host
		RestAssured.baseURI = "http://216.10.245.166";
//		String bodyContent = "{\r\n\"name\":\"Learn Appium Automation with Java\","
//				+ "\r\n\"isbn\":\"bfcd\",\r\n\"aisle\":\"8834\",\r\n\"author\":\"Sandeep Parihar\"\r\n}";

		Response res = RestAssured.given().
				headers("Content-Type","application/json").
			//body(bodyContent).
			body(jsonAsMap).  //pass the json object in the body

		// In When block we need to pass resource information - get(resource) post(resource) put(resource)
				when().
					post("/Library/Addbook.php").

		// In Then block we need to do assertions to validate the response body and response headers
				then().
					assertThat().statusCode(200).
					and().body("Msg", equalTo("successfully added")).
		
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
