import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserJson {

	// Primeiro nivel do Json
	
	@Test
	public void validarCampos() {
		
		given()
		.when()
			.get("https://restapi.wcaquino.me/users/1")
		.then()
			.statusCode(200)
			.body("id", Matchers.is(1))
			.body("id", Matchers.isA(Integer.class))
			.body("name", Matchers.containsString("Silva"))
			.body("name", Matchers.isA(String.class))
			.body("age", Matchers.greaterThan(18))
		;
	}
	
	@Test
	public void validarCampos2() {
		
		Response resposta = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/users/1");
		
		//path
		System.out.println(resposta.path("id"));
		
		// jsonpath
		JsonPath jpath = new JsonPath(resposta.asString());
		Assert.assertEquals(1, jpath.getInt("id"));
		
		int id = JsonPath.from(resposta.asString()).getInt("id");
		Assert.assertEquals(1, id);
	}
	
	@Test
	public void casoDeErro() {
		
		given()
		.when()
			.get("https://restapi.wcaquino.me/users/5")
		.then()
			.statusCode(404)
			.body("error", Matchers.is("Usuário inexistente"))
		;
	}
	
	// Segundo nivel do Json

	@Test
	public void validarCampos3() {
		
		given()
		.when()
			.get("https://restapi.wcaquino.me/users/2")
		.then()
			.statusCode(200)
			.body("name", Matchers.containsString("Joaquina"))
			.body("endereco.rua", is("Rua dos bobos"))
		;
	}
	
	
	// Lista Json
	@Test
	public void validarCampos4() {
		
		given()
		.when()
			.get("https://restapi.wcaquino.me/users/3")
		.then()
			.statusCode(200)
			.body("name", Matchers.containsString("Ana"))
			.body("filhos", Matchers.hasSize(2))
			.body("filhos[0].name", is("Zezinho"))
			.body("filhos[1].name", is("Luizinho"))
			.body("filhos.name", Matchers.hasItem("Zezinho"))
		;
	}
	
	
}
