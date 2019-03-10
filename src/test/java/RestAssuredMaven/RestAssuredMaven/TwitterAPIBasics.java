package RestAssuredMaven.RestAssuredMaven;

import org.testng.annotations.Test;

import files.Utilities;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TwitterAPIBasics {
	
	String consumerKey = "";
	String consumerSecret = "";
	String token = "";
	String tokenSecret = "";
	String tweetId;
	
		
	@Test
	public void getLatestTweet() {

		RestAssured.baseURI = "https://api.twitter.com/1.1/statuses";
		// OAuth Authentication
		Response res = RestAssured.given().
		auth().oauth(consumerKey, consumerSecret, token, tokenSecret).
		//for OAuth 1.0 we need to add ScribeJava Core jar and ScribeJava APIs jar version 2.5.3
		//for OAuth 2.0 we do not need to add any extra jar
		queryParam("count", "1").
		when().
		get("/home_timeline.json").
		then().
		assertThat().statusCode(200).
		extract().response(); //returns raw format in the res object
		
		JsonPath js = Utilities.rawToJSON(res); //Using rawToJson method to convert raw string to JSON object
		String tweetText = js.get("text");
		String id = js.get("id");
		System.out.println("Text of latest tweet is: "+tweetText);
		System.out.println("ID of latest tweet is: "+id);
		
}
	
	@Test
	public void createTweet() {

		RestAssured.baseURI = "https://api.twitter.com/1.1/statuses";
		// OAuth Authentication
		Response res = RestAssured.given().
		auth().oauth(consumerKey, consumerSecret, token, tokenSecret).
		queryParam("status", "Creating tweet using Rest API Automation").
		when().
		post("/update.json").
		then().
		assertThat().statusCode(200).
		extract().response(); //returns raw format in the res object
		
		JsonPath js = Utilities.rawToJSON(res); //Using rawToJson method to convert raw string to JSON object
		String tweetText = js.get("text");
		tweetId = js.get("id").toString();
		System.out.println("Below is the tweet we added:: ");
		System.out.println("Text of latest tweet we tweeted is: "+tweetText);
		System.out.println("ID of latest tweet we tweeted is: "+tweetId);
		
}
	
	@Test
	public void deleteTweet() {

		RestAssured.baseURI = "https://api.twitter.com/1.1/statuses";
		// OAuth Authentication
		Response res = RestAssured.given().
		auth().oauth(consumerKey, consumerSecret, token, tokenSecret).
		when().
		post("destroy/"+tweetId+".json").
		then().
		assertThat().statusCode(200).
		extract().response(); //returns raw format in the res object
		
		JsonPath js = Utilities.rawToJSON(res); //Using rawToJson method to convert raw string to JSON object
		String truncated = js.get("truncated");
		String tweetText = js.get("text");
		System.out.println("Below is the tweet we deleted:: ");
		System.out.println("Tweet not deleted? "+truncated);
		System.out.println("Text of deleted tweet is: "+tweetText);
		
}
	
}
