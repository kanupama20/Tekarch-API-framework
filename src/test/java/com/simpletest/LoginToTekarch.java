package com.simpletest;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pojo.LoginRequestPOJO;
import com.pojo.LoginResponsePOJO;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class LoginToTekarch {

	@BeforeMethod
	public static void setup() {
		RestAssured.baseURI = "https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net/";
	}
	
	@Test
	public static void loginToApi() {
		
		LoginRequestPOJO loginReq = new LoginRequestPOJO();
		loginReq.setUsername("kanupama20@gmail.com");
		loginReq.setPassword("kanupama123");
		
		Response res = RestAssured
		.given()
		.body(loginReq)
		.contentType(ContentType.JSON)
		.when()
		.post("login");
		
		res.then().statusCode(201);
		String token  = res.jsonPath().get("[0].token");
		String userId  = res.jsonPath().get("[0].userid");
		System.out.println("Token =  "+token);		
		System.out.println("Userid =  "+userId);		
		res.prettyPrint();
		
		LoginResponsePOJO[] list = res.as(LoginResponsePOJO[].class);
		String token1 = list[0].getToken();
		System.out.println("Token from POJO = "+ token1);
	}
	
	public static String GetLogin_Token() {
		Response res = RestAssured
				.given()
				.body("{\"username\":\"kanupama20@gmail.com\",\"password\":\"kanupama123\"}")
				.contentType(ContentType.JSON)
				.when()
				.post("login");
		String token  = res.jsonPath().get("[0].token");
		return token;
	}
	
	@Test
	public static void getUser_Data() {
		String token1 = GetLogin_Token();
		System.out.println("Token = "+ token1);
		Header header = new Header("token", token1);
		
		Response res = RestAssured
				.given()
				.header(header)
				.when()
				.get("getdata");
		
		res.then().statusCode(200);
		System.out.println("no of records = "+ res.jsonPath().get("size()"));				
	}
	
	public static void create_user() {
		
		String token1 = GetLogin_Token();
		System.out.println("Token = "+ token1);
		Header header = new Header("token", token1);
		
		Response res = RestAssured.given()
		.body("{\"accountno\":\"TA-Aug2333\",\"departmentno\":\"4\",\"salary\":\"7890\",\"pincode\":\"123123\"}")
		.contentType(ContentType.JSON)
		.header(header)
		.when()
		.post("addData");
		
		res.then().statusCode(201);
		res.then().body("status",Matchers.equalTo("success"));
	}
	
	public static void updateuser_data() {
		
		String token1 = GetLogin_Token();
		System.out.println("Token = "+ token1);
		Header header = new Header("token", token1);
		
		Response res = RestAssured.given()
		.contentType(ContentType.JSON)
		.header(header)
		.body("{\"accountno\":\"TA-Aug2333\",\"departmentno\":3,\"salary\":12000,\"pincode\":123123,\"userid\":\"R3zHMCMybn4kzH04QFyi\",\"id\":\"6CcqXyItFXrMlndueF9G\"}")
		.when()
		.put("updateData");
		
		res.then().statusCode(200);
		res.then().body("status",Matchers.equalTo("success"));
	}
	
	public static void delete_user() {
		String token1 = GetLogin_Token();
		System.out.println("Token = "+ token1);
		Header header = new Header("token", token1);
		
		Response res = RestAssured.given()
				.contentType(ContentType.JSON)
				.header(header)
				.body("{\"userid\":\"R3zHMCMybn4kzH04QFyi\",\"id\":\"6CcqXyItFXrMlndueF9G\"}")
				.when()
				.put("deleteData");
				
				res.then().statusCode(200);
				res.then().body("status",Matchers.equalTo("success"));
				res.then().contentType(ContentType.JSON);		
	}
}
