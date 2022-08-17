package Serialization;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
public class SerializeTest {
	
	
	@Test	
	public static void  Serialization() {
	bodyelements bd = new bodyelements();
	bd.setAccuracy(50);
	bd.setAddress("1735 parkhurst avenue");
	bd.setLanguage("English");
	bd.setName("Kuldeep");
	bd.setWebsite("www.yahoo.com");
	bd.setPhone_number("2269984576");
	
	List<String> myList= new ArrayList<String>();
	myList.add("shop");
	myList.add("Mobile");
	bd.setTypes(myList);
	
	Location l= new Location();
	l.setLat(3.2555);
	l.setLng(-2.5258);
	
	bd.setLocation(l);

	RestAssured.baseURI="https://rahulshettyacademy.com"; 
	String details = given().log().all().queryParam("key","qaclick123").body(bd).
	 when().post("/maps/api/place/add/json").then().log().all().statusCode(200).extract().response().asString();
	
	Assert.assertEquals("","");
	
	
	}
	
}