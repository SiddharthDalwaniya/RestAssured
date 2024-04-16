package Tests;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.matcher.RestAssuredMatchers;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.apache.commons.io.IOUtils;
//import org.testng.annotations.Test;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SchemaValidation {

	@Test
	public void JSONSchemaValidations() {
		baseURI = "https://reqres.in/api";
		given().accept(ContentType.JSON).queryParam("page", 2).when().get("/users").then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("./validators/schema.json")).statusCode(200);
	}

	@Test
	public void SOAPSchemaValidation() throws FileNotFoundException, IOException {
		baseURI = "https://ecs.syr.edu";

		File file = new File("./validators/AddIntegerSOAP.xml");
		FileInputStream input = new FileInputStream(file);

		File file2 = new File("./validators/Schema.xsd");
		FileInputStream input2 = new FileInputStream(file2);
		String request = IOUtils.toString(input, "UTF-8");
		given().contentType("text/xml").accept(ContentType.XML).header("SOAPAction", "http://tempuri.org/Add")
				.body(request).when().post("/faculty/fawcett/Handouts/cse775/code/calcWebService/Calc.asmx").then()
				.log().all().and()//;
//	    body("//AddResult", equalTo(0)).and().
				.assertThat().body(RestAssuredMatchers.matchesXsdInClasspath("./validators/SOAPSchema.xsd"));

	}

}
