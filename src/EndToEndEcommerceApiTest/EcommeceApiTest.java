package EndToEndEcommerceApiTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import EndToEndEcommerceApiTest.LoginReq;
import EndToEndEcommerceApiTest.LoginResponse;

public class EcommeceApiTest {
	@Test
	public static void main() {
		//SSL certification After given() use .relaxedHTTPSValidation()
		// when you try to run the test if shows the error inavlid certification or certification is not valid.
		RequestSpecification req =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON).build();
		
		LoginReq loginRequest = new LoginReq();
		loginRequest.setUserEmail("dabhi.kuldeep30595@gmail.com");
		loginRequest.setuserPassword("Aayan2010kd!");
		
		RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequest);
		LoginResponse loginResponse =reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
	
		String token = loginResponse.getToken();
		String userId = loginResponse.getUserId();
		System.out.println(loginResponse.getToken());
		System.out.println(loginResponse.getUserId());
		
		//Add Product
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();
		
		RequestSpecification reqAddProduct =given().log().all().spec(addProductBaseReq).param("productName", "car")
		.param("productAddedBy", userId).param("productCategory","Automobile").param("productSubCategory","car")
		.param("productPrice","1700").param("productDescription","new car").param("productFor","all")
		.multiPart("productImage",new File("C:\\Users\\dabhi\\OneDrive\\Pictures\\Saved Pictures\\1.jpg"));
		
		String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product").
		then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(addProductResponse);
		String productId = js.get("productId");
		System.out.println(productId);
		
		
		//Create Order
		RequestSpecification placeOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON).build();
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);
		
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		orderDetailList.add(orderDetail);
		
		Orders orders = new Orders();
		orders.setOrders(orderDetailList);
		
		
		RequestSpecification placeOrderReq =given().log().all().spec(placeOrderBaseReq).body(orders);
		String responseAddOrder = placeOrderReq.when().post("/api/ecom/order/create-order")
		.then().log().all().extract().response().asString();
		
		System.out.println(responseAddOrder);
		
		//Delete Product
		RequestSpecification deleteProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON).build();
		
		RequestSpecification deleteProd = given().log().all().spec(deleteProductBaseReq).pathParam("productId", productId);
		String deleteProdRes = deleteProd.when().delete("/api/ecom/product/delete-product/{productId}")
				.then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(deleteProdRes);
		System.out.println(js1.getString("message"));
		Assert.assertEquals("Product Deleted Successfully",js1.getString("message"));
	}

}
