package RestAssuredMaven.RestAssuredMaven;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.Utilities;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class JiraAPIBasics {

	Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/java/files/environment.properties");
		prop.load(fis);
		prop.getProperty("JIRAHOST");
	}

	@Test
	public void JiraAPI() {

		RestAssured.baseURI = prop.getProperty("JIRAHOST");
		;
		// Create the session
		Response res = RestAssured.given().headers("Content-Type", "application/json")
				.body("{ \"username\": \"sparihar08\", \"password\": \"parihar_08\" }").
				when().post("rest/auth/1/session").then().assertThat().statusCode(200).
				extract().response(); //returns raw format in the res object
		
		JsonPath js = Utilities.rawToJSON(res); //Using rawToJson method to convert raw string to JSON object
		String sessionId = js.get("session.value");
		System.out.println("Session ID generated is: "+sessionId);
		
		//Creating JIRA issue
		Response create = RestAssured.given().headers("Content-Type", "application/json").headers("Cookie","JSESSIONID="+sessionId).
		body("{ "+
    "\"fields\": {"+
        "\"project\": {"+
            "\"key\": \"TEST\""+
        "},"+
        "\"summary\": \"REST API Automation Testing - Debit Card Defect\","+
        "\"description\": \"Test Description for debit card defect automation test\","+
        "\"issuetype\": {"+
            "\"name\": \"Bug\""+
        		"}"+
    				"}}").
		when().post("rest/api/2/issue").
		then().statusCode(201).
		extract().response();
		
		JsonPath jp = Utilities.rawToJSON(create); //Using rawToJson method to convert raw string to JSON object
		String id = jp.get("id");
		System.out.println("ID generated after creating new Jira issue is: "+id);
		
	}

}
