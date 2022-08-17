import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumofCoursesTest {
	
	@Test
	public void SumOfCourses() {
		//Verify if Sum of all Course prices matches with Purchase Amount
				JsonPath js = new JsonPath(payload.CoursePrice());
				int count = js.getInt("courses.size()");
				int	totalPrice = 0;
				for(int i=0;i<count;i++) {
				int	price=js.getInt("courses["+i+"].price");
				int	copies=js.getInt("courses["+i+"].copies");
				
				int coursePrice = price* copies;
				totalPrice =totalPrice+ coursePrice;
				
				}
				System.out.println(totalPrice);
			int	purchaseAmt = js.getInt("dashboard.purchaseAmount");
				Assert.assertEquals(totalPrice,purchaseAmt);
			}
		
	
	}

