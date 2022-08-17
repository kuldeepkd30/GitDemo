import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class StaticJson {
	//if jason is static and we do not need to change data into json file we can directly use the json file given to us 
	//without integrating the file into project file
	//we have to call the external file 
	//1st step=> we need the data in String format for .body()
	//we have java method which read the content from file and convert the content into bytes format
	//2nd steep=> convert bytes data to String
	//1st=> .body(Files.readAllBytes(Paths.get("C:\\jsonFiles\\addplace.json")))
	//2nd=> .body(new String(Files.readAllBytes(Paths.get("C:\\jsonFiles\\addplace.json"))))
	
	// file => C:\jsonFiles\addplace.json
	@Test
	public void addbook() throws IOException {
		RestAssured.baseURI="http://216.10.245.166";
	String rensp =	given().log().all().header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\jsonFiles\\addplace.json"))))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		System.out.println(rensp);
		JsonPath js = new JsonPath(rensp);
		String bookId= js.get("ID");
	/*	
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
	*/
}
}
