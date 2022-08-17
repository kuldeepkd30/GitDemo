import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;

import files.payload;
public class basic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//validate if Add Place API is Working
		//given- all input details
		//when- Submit the API
		//then- Validate the response
		
	/*prog1	
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaquick123").header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		 .header("server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();

		System.out.println(response);
		
	*/
		//prog2
		//add place-> Update place with new address -> Get place to validate the address is present in response
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		 .header("server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();

		System.out.println(response);
		
		JsonPath js = new JsonPath(response);//used to parse the jason and extract the value we want. need string as input
		String placeId = js.getString("place_id");
		System.out.println(placeId);
		
	//put api to update the address
		String newAdd = "175 Shaugnessy Blvd";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAdd+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("/maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
	
		//get api to check and verify the address
		
		String updated=given().log().all().queryParam("key", "qaclick123").queryParam("place_id",placeId)
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(updated);
		JsonPath js1 = new JsonPath(updated);
		String newPlace = js1.getString("address");
		System.out.println(newPlace);
		
		Assert.assertEquals(newAdd,newPlace );  
		
	}

}
