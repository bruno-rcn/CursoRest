import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.text.StringContainsInOrder;
import org.junit.Assert;
import org.junit.Test;

import groovyjarjarantlr4.v4.parse.ANTLRParser.notSet_return;
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
	
	@Test
	public void testeHamcrest() {
		
		// primeiro parametro vem da aplicacao e o segundo é o do codigo
		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat(128, Matchers.is(128));
		Assert.assertThat(128, Matchers.isA(Integer.class));
		Assert.assertThat(128d, Matchers.greaterThan(120d)); // maior que
		
		// Utilizando o import statico em assert e matcher
		List<Integer> impares = Arrays.asList(1, 3, 5, 7);
		assertThat(impares, hasSize(4));
		assertThat(impares, contains(1, 3, 5, 7));
		assertThat(impares, containsInAnyOrder(5, 3, 7, 1));
		assertThat(impares, Matchers.hasItem(1));
		assertThat(impares, Matchers.hasItems(3, 5));
		
	}

}
