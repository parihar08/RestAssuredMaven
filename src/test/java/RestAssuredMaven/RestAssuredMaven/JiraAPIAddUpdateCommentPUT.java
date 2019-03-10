package RestAssuredMaven.RestAssuredMaven;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import files.Resources;
import files.Utilities;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class JiraAPIAddUpdateCommentPUT {
	
	
	public static Logger log = Logger.getLogger(JiraAPIAddUpdateCommentPUT.class.getName());
	Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/java/files/environment.properties");
		prop.load(fis);
		prop.getProperty("JIRAHOST");
	}

	@Test
	public void JiraAPI() throws IOException {
		
		//Creating JIRA issue
		log.info("Host Information: "+prop.getProperty("JIRAHOST"));
		log.info("Creating JIRA issue !!!");
		String sessionID = Resources.getJiraSessionKey(); //Get session id from reusable method in Resources class
		Response create = RestAssured.given().headers("Content-Type", "application/json").
				headers("Cookie","JSESSIONID="+sessionID). 
		body("{ "+
    "\"fields\": {"+
        "\"project\": {"+
            "\"key\": \"TEST\""+
        "},"+
        "\"summary\": \"REST API Automation Testing - Credit Card Defect\","+
        "\"description\": \"Test Description for credit card defect automation test\","+
        "\"issuetype\": {"+
            "\"name\": \"Bug\""+
        		"}"+
    				"}}").
		when().post("rest/api/2/issue").
		then().statusCode(201).
		extract().response();
		log.info("JIRA issue created!!!");
		
		JsonPath jp = Utilities.rawToJSON(create); //Using rawToJson method to convert raw string to JSON object
		String id = jp.get("id");
		System.out.println("ID generated after creating new Jira issue is: "+id);
		log.info("ID generated after creating new Jira issue is: "+id);
		
		//Adding comment to JIRA issue using POST request
		//RestAssured.baseURI = "http://localhost:8080";
		log.info("Adding comment to JIRA issue !!!");
		Response comment = RestAssured.given().headers("Content-Type", "application/json").
				headers("Cookie","JSESSIONID="+sessionID).
				body("{"+
				    "\"body\": \"Inserting comment using Rest API automation testing\","+
				    "\"visibility\": {"+
				        "\"type\": \"role\","+
				        "\"value\": \"Administrators\" }"+
				"}").
				//when().post("rest/api/2/issue/10039/comment").
				when().post("/rest/api/2/issue/"+id+"/comment").
				then().statusCode(201).
				extract().response();
			log.info("Added comment to JIRA issue !!!");
				
				JsonPath js = Utilities.rawToJSON(comment); //Using rawToJson method to convert raw string to JSON object
				String commentId = js.get("id");
				System.out.println("Comment ID generated after adding a comment to existing Jira issue is: "+commentId);
				log.info("Comment ID generated after adding a comment to existing Jira issue is: "+commentId);
				
				
		//Updating comment in an JIRA issue using PUT request
		
		log.info("Updating comment in an JIRA issue !!!");		
		Response update = RestAssured.given().headers("Content-Type", "application/json").
				headers("Cookie","JSESSIONID="+sessionID).
				//pathParams("comment_id","10103").
				body("{"+
						    "\"body\": \"Updating comment using Rest API automation testing\","+
						    "\"visibility\": {"+
						        "\"type\": \"role\","+
						        "\"value\": \"Administrators\" }"+
						"}").
				//when().put("rest/api/2/issue/10045/comment/10023").
				
				when().
				put("/rest/api/2/issue/"+id+"/comment/"+commentId+"").
				//put("/rest/api/2/issue/"+id+"/comment/{comment_id}").
				then().statusCode(200).
				extract().response();
				log.info("Updated comment in an JIRA issue !!!");		
						
				JsonPath jsp = Utilities.rawToJSON(update); //Using rawToJson method to convert raw string to JSON object
				String commentId2 = jsp.get("id");
				System.out.println("Comment ID generated after updating a comment to existing Jira issue is: "+commentId2);
				log.info("Comment ID generated after updating a comment to existing Jira issue is: "+commentId2);
				
	}
	
	

}
