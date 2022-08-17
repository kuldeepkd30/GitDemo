import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
public class DynamicJson {
	
	@Test(dataProvider="booksData")
	public void addbook(String isbn, String aisle) {
		RestAssured.baseURI="http://216.10.245.166";
	String rensp =	given().log().all().header("Content-Type","application/json")
		.body(payload.Addbook(isbn, aisle))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		System.out.println(rensp);
		JsonPath js = new JsonPath(rensp);
		String bookId= js.get("ID");
		
		//Delete book so we can use same data again for create book 
		
		//:/Library/DeleteBook.php
		given().log().all().header("Content-Type","application/json")
		.body(payload.DeleteBook(bookId))
		.when().post("Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
	}
	//Object[][]{{"",""},{"",""},{"",""},{"",""},{"",""}};  Multidimensional array = collection of arrays
	//Object []{"",""}; Single array= collection of data
	
	//data provider is used when we want to run the same test multiple time with diffrent sets of data
	@DataProvider(name="booksData")
	public Object[][] getData(){
		
	return new Object[][]{{"pqzm","1056"},{"lahd","4567"},{"zxcv","7890"},{"poiu","3579"},{"lkjh","0864"}};
	}
}
