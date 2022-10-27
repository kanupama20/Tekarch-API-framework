package com.simpletest;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SimpleCRUD {

	@Test	
	public static void firstTest() {
		
		RestAssured
			.given()
			.when()
			.get("https://gorest.co.in/public-api/users/")
			.then()
			.statusCode(200)
			.time(Matchers.lessThan(2000L))	
			//.prettyPrint();
			//System.out.println("Response status code = "+ res.statusCode());
			//System.out.println("Response time = "+ res.getTime());
			//System.out.println("Header = "+ res.header("alt-svc"));
			.body("code", Matchers.equalTo(200))
			.body("data[0].id", Matchers.equalTo(3029));
	}
	
	@Test	
public static void firstTest1() {
		
		RequestSpecification req = RestAssured.given();
		
		Response res = req
			//.given()
			.when()
			.get("https://gorest.co.in/public-api/users/");
			
			res.then().statusCode(200);
			res.then().time(Matchers.lessThan(2000L));
			res.prettyPrint();
			System.out.println("Response status code = "+ res.statusCode());
			System.out.println("Response time = "+ res.getTime());
			System.out.println("Header = "+ res.header("alt-svc"));
			res.then().body("code", Matchers.equalTo(200));
			res.then().body("data[0].id", Matchers.equalTo(3029));
	}
	
}
