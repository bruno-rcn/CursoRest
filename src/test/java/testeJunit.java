import static io.restassured.RestAssured.given;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class testeJunit {

	@Test
	public void testeOlaMundo() {
		
		// Utilizando sysout 
		Response request = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");

		System.out.println(request.getBody().asString().equals("Ola Mundo!"));
		System.out.println(request.statusCode() == 200);
		System.out.println("---------------------------------------------------");
		System.out.println(request.getBody().asString().equals("Oa Mundo!"));
		System.out.println(request.statusCode() == 20);

		ValidatableResponse validacao = request.then();
		validacao.statusCode(200);
		
		System.out.println("---------------------------------------------------");
		
		// Utilizando o Assert do org.junit
		Assert.assertTrue(request.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue(request.statusCode() == 200);
		
		// primeiro parametro é o do codigo e o segundo é o que vem da aplicacao
		Assert.assertEquals(request.statusCode(), 200);
	}
	
	@Test
	public void testeOlaMundoDeManeiraDireta() {
		
//		Response request = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");
//		ValidatableResponse validacao = request.then();
//		validacao.statusCode(200);
		
		// pode realizar o import statico do RestAssured
		RestAssured.get("https://restapi.wcaquino.me/ola").then().statusCode(200);
	}
	
	@Test
	public void testeOlaMundoGWT() {
		
		given()
		.when()
			.get("https://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200);
	}

}
