package files;

public class PayLoad {
	
	public static String getPostBody(){
		
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
		
		return bodyContent;
		
	}
	
	public static String addBook(String isbnNo, String aisleNo){
		
		String addBook = "{\r\n\"name\":\"Learn Appium Automation with Java\","
				+ "\r\n\"isbn\":\""+isbnNo+"\",\r\n\"aisle\":\""+aisleNo+"\",\r\n\"author\":\"Sandeep Parihar\"\r\n}";
		
		return addBook;
	}

}
