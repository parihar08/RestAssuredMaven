package RestAssuredMaven.RestAssuredMaven;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.PayLoad;

public class DataParameterizationTest {

	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String aisle) {	
		// Provide Base url or host
		RestAssured.baseURI = "http://216.10.245.166";

		// In Given block we need to pass components - request headers, request parameters, request cookies, body
		Response res = RestAssured.given().
				headers("Content-Type","application/json").
			//queryParam("key", "qaclick123").
			body(PayLoad.addBook(isbn,aisle)).   //Dynamic JSON

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
	
	@DataProvider(name="BooksData")
	public Object[][] getData(){
		//Create multi-dimensional array
		Object[][] data = new Object[][] { { "abc", "1236" }, { "def", "4561" }, { "ghi", "7890" } };
		return data;
	}

}
