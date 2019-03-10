package RestAssuredMaven.RestAssuredMaven;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.XmlConfig;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;
import files.Utilities;


public class CreateAndDeletePlaceXMLTest {

	@Test()
	public void Test1() throws IOException {
		// Provide Base url or host
		RestAssured.baseURI = "http://216.10.245.166";
	
		//Getting body in the form of string using  generateStringFromResource method
		String bodyContent = generateStringFromResource(System.getProperty("user.dir")+"/src/test/java/files/postbody.xml");
		
		// In Given block we need to pass components - request headers, request parameters, request cookies, body
		Response res = RestAssured.given().config(RestAssuredConfig.config().xmlConfig(XmlConfig.xmlConfig().disableLoadingOfExternalDtd())).
			queryParam("key", "qaclick123").
			header("Content-Type","application/xml").
			body(bodyContent).

		// In When block we need to pass resource information - get(resource) post(resource) put(resource)
				when().
					post("/maps/api/place/add/xml").

		// In Then block we need to do assertions to validate the response body and response headers
				then().
					assertThat().statusCode(200).
					
					//and().contentType(ContentType.XML). //Choose Content Type as XML
		
		// Create place > returns response place_id

		// In Extract block we can pull out the body response in to some variable for future usage
			extract().response(); //returns raw format in the res object
//			String responseString = res.asString(); // converts raw format to String
//			System.out.println("Raw string response for the create request: "+ responseString);
			
//			<?xml version="1.0"?>
//			<response>
//			    <status>OK</status>
//			    <place_id>fdd2f7bf74afae3f1462833d43a53678</place_id>
//			    <scope>APP</scope>
//			    <reference>326f3c1e773a000ee7b0f1ed529f900e326f3c1e773a000ee7b0f1ed529f900e</reference>
//			    <id>326f3c1e773a000ee7b0f1ed529f900e</id>
//			</response>

			
		//Convert the String in to XML format to traverse through
			XmlPath xp = Utilities.rawToXML(res); //converts the string into json object
			String placeid = xp.get("response.place_id"); //pass the string path from create response which we want to access and returns us the value
			System.out.println("Place id: "+placeid +" converted to XML object succcessfully");
			
		// Delete place > Now send this placeid in the delete request body
//			RestAssured.given().
//				queryParam("key", "qaclick123").
//				body("{"+
//				"\"place_id\" : \""+placeid+"\" "+  // placeid sent in the delete request body
//						"}").         
//			when().
//				post("/maps/api/place/delete/json").
//			then().
//				assertThat().statusCode(200).
//				and().contentType(ContentType.JSON).
//				and().body("status", equalTo("OK"));
//			System.out.println("Place id: "+placeid +" deleted succcessfully");
			
	}
	
	//Method to generate string from XML xpath
	public static String generateStringFromResource(String path) throws IOException{
		return new String(Files.readAllBytes(Paths.get(path)));
	}

}
