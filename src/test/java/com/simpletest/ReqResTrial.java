package com.simpletest;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class ReqResTrial {
	@Test
	public static void list_users() {
		
		RequestSpecification req = RestAssured.given();
		Response res = req.when()
				.get("https://reqres.in/api/users?page=2");
		res.then()
		.statusCode(200);
		res.prettyPrint();
		int count = res.body().jsonPath().get("data.size()");
		System.out.println("no of users = "+ count);		
	}
	
	@Test
	public static void single_user() {
		
		RequestSpecification req = RestAssured.given();
		Response res = req.when()
				.get("https://reqres.in/api/users/2");
		
		res.then().statusCode(200);
		res.prettyPrint();
		int id = res.jsonPath().get("data.id");
		System.out.println("user ID = "+id);
		
	}
	
	@Test
	public static void singleUser_notFound() {
		Response res = RestAssured.given()
		.when()
		.get("https://reqres.in/api/users/23");
		
		res.then().statusCode(404);
		res.prettyPrint();		
	}
	
	@Test
	public static void list_resource() {
		
		Response res = RestAssured.given()
				.when()
				.get("https://reqres.in/api/unknown");
		
		res.then().statusCode(200);
		res.prettyPrint();
		int pages = res.jsonPath().get("total_pages");
		System.out.println("No of pages = "+pages);
	}
	
	@Test
	public static void single_resource() {
		
		Response res = RestAssured.given()
				.when().get("https://reqres.in/api/unknown/2");
		
		res.then().statusCode(200);
		res.prettyPrint();
		String url = res.jsonPath().get("support.url");
		System.out.println("URL = "+url);
	}
	
	@Test
	public static void singleResource_notFound() {
		
		Response res = RestAssured.given()
				.when().get("https://reqres.in/api/unknown/23");
		
		res.then().statusCode(404);
		res.prettyPrint();
	}
	
	@Test
	public static void create_user() {
		
		Response res = RestAssured.given()
				.when()
				.body("{\"name\":\"morpheus\",\"job\":\"leader\"}")
				.post("https://reqres.in/api/users");
		res.then().statusCode(201);
		res.prettyPrint();
		String id1 = res.jsonPath().get("id");
		System.out.println("ID = "+id1);
	}
	
	public static void update_user() {
		
		
		
	}
	
}

	
