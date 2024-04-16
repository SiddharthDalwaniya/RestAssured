package Tests;

import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.matcher.ResponseAwareMatcher.*;
import static org.hamcrest.Matchers.*;

public class ReqResTest {

	@Test(priority = 0)
	public void testGet1() throws IOException {
		
		System.out.println("<<<--------------Get Basic Test---------------->>>\n");
		
		Response response=RestAssured.get("https://reqres.in/api/users?page=2");
		System.out.println(response.getStatusCode());
		System.out.println(response.getContentType());
		System.out.println(response.getHeader(ARRAY_MISMATCH_TEMPLATE));
		System.out.println(response.getTime());
		System.out.println(response.getBody().asString());
		FileWriter writer=new FileWriter(new File("response.txt"));
		writer.write(response.getBody().asString());
		writer.close();
		
		System.out.println("\n\n");
	}
	
	@Test (priority=1)
	public void testGet2() {
		System.out.println("<<<--------------validate details of the child using perticular parent Test---------------->>>");
		baseURI="https://reqres.in/api";
		given().
			get("/users?page=2").
		then().
			body("data[0].id", equalTo(7)).log().all().
			body("data.first_name",hasItems("Michael","Lindsay")).
				and().
			body("data.find{it.first_name='Michael'}.last_name",equalTo("Lawson"));
	}
	
	@Test(priority=2)
	public void testPost1() {
		System.out.println("\n\n<<<-------------Post basic Test---------------->>>\n");
		
		// define the base URL
		baseURI="https://reqres.in";
		
		// Create JSON object to pass into the body
		JSONObject request=new JSONObject(); //JSON Simple library should be add in the dependency in order to use JSONObject
		request.put("Name", "Sid");
		request.put("Name","Priya");
		
		// Post the request and validate the response code 201 for successful entry creation 
		given().
			contentType(ContentType.JSON). // Informing the server that request is the type of JSON
			accept(ContentType.JSON). //Accept the JSON response
			body(request.toJSONString()). //Send the JSON string to the server
		when().
			post("/api/users"). // Post the request to the particular path or endpoint
		then().
			log().all().
			statusCode(201); //Validate the response
	}
	
	@Test(priority=3)
	public void testPut1() {
		System.out.println("\n\n<<<-----------Put basic Test---------------->>>\n");
		
		baseURI="https://reqres.in";
		
		JSONObject request=new JSONObject();
		request.put("name", "Sid");
		request.put("name", "Priya");
		
		given().
			accept(ContentType.JSON).
			contentType(ContentType.JSON).
			body(request.toJSONString()).
		when().
			put("/api/users/2"). // Use put method to put the body to the endpoint and update the entries completely
		then().
			log().all().
			statusCode(200);
	}
	
	@Test(priority=4)
	public void testPatch1() {
		System.out.println("\n\n<<<-----------Patch basic Test---------------->>>\n");
		
		baseURI="https://reqres.in";
		
		JSONObject request=new JSONObject();
		request.put("name", "Sid");
		request.put("name", "Priya");
		
		given().
			accept(ContentType.JSON).
			contentType(ContentType.JSON).
			body(request.toJSONString()).
		when().
			patch("/api/users/2"). // Use put method to patch the body to the endpoint and update the entries partially
		then().
			log().all().	
			statusCode(200);
			
	}
	
	@Test(priority=5)
	public void testDelete1() {
		System.out.println("\n\n<<<-----------Delete basic Test---------------->>>\n");
		
		baseURI="https://reqres.in";
		
		JSONObject request=new JSONObject();
		request.put("name", "Sid");
		request.put("name", "Priya");
		
		given().
			accept(ContentType.JSON).
			contentType(ContentType.JSON).
//			body(request.toJSONString()).
		when().
			delete("/api/users/2"). // Use put method to patch the body to the endpoint and update the entries partially
		then().
			log().all().
			statusCode(204);
	}
	
	
	
}
