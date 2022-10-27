package com.simpletest;

import java.util.List;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AssgnmentWebServices {
	
@Test
	public static void assignment() {
		RequestSpecification req = RestAssured.given();
		Response res = req
		.when()
		.get("https://dummy.restapiexample.com/api/v1/employees");
		res.then()
		.time(Matchers.lessThan(2000L));
		res.prettyPrint();
		
		res.then().body("status", Matchers.equalTo("success"));
		System.out.println("Status = "+res.statusCode());
		
		int count  = res.jsonPath().getInt("data.size()");
		System.out.println("Total Employees = "+ count);
		
		String EmpName = "Herrod Chandler";
		String EmpName1 = res.jsonPath().get("data.find{it->it.id ==7}.employee_name");
		Assert.assertEquals(EmpName1, EmpName);
		
		List list = res.jsonPath().getList("data.findAll{it->it.employee_salary > 300000}");
		System.out.println("Employee detais with Salary > 300000 = \n"+list);
		
		List list1 = res.jsonPath().get("data.findAll{it->it.employee_age > 60}.employee_name");
		System.out.println("Employee detais with age > 60 = \n"+list1);

	}
	
}
