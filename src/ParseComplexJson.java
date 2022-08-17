import files.payload;
import io.restassured.path.json.JsonPath;

public class ParseComplexJson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(payload.CoursePrice());
		
		//1. Print No of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		//2.Print Purchase Amount
		int totalAmt = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmt);
		
		//3. Print Title of the first course
		String FirstTitle = js.get("courses[0].title");
		System.out.println(FirstTitle);
		
		//4. Print All course titles and their respective Prices
		for(int i=0;i<count;i++) {
		//System.out.println(js.get("courses["+i+"].title").toString());
		System.out.println(js.getString("courses["+i+"].title"));
		System.out.println(js.getInt("courses["+i+"].price"));
	
		} 
		//5. Print no of copies sold by RPA Course
		for(int i=0;i<count;i++) {
			String Title=js.getString("courses["+i+"].title");
			if(Title.contains("RPA")) {
				System.out.println(js.getInt("courses["+i+"].copies"));
				
			}
			break;
		}
		
	//Verify if Sum of all Course prices matches with Purchase Amount
		int	totalPrice = 0;
		for(int i=0;i<count;i++) {
		int	price=js.getInt("courses["+i+"].price");
		int	copies=js.getInt("courses["+i+"].copies");
		
		int coursePrice = price* copies;
		totalPrice =totalPrice+ coursePrice;
		
		}
		System.out.println(totalPrice);
		
	}

}
/*
 * Json used in this Section with Queries to solve
{

"dashboard": {

"purchaseAmount": 910,

"website": "rahulshettyacademy.com"

},

"courses": [

{

"title": "Selenium Python",

"price": 50,

"copies": 6

},

{

"title": "Cypress",

"price": 40,

"copies": 4

},

{

"title": "RPA",

"price": 45,

"copies": 10

}

]

}



1. Print No of courses returned by API

2.Print Purchase Amount

3. Print Title of the first course

4. Print All course titles and their respective Prices

5. Print no of copies sold by RPA Course

6. Verify if Sum of all Course prices matches with Purchase Amount
 */

