import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserJson {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "https://restapi.wcaquino.me";
	}

	// Primeiro nivel do Json
	
	@Test
	public void validarCampos() {
		
		given()
		.when()
			.get("/users/1")
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
		
		Response resposta = RestAssured.request(Method.GET, "/users/1");
		
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
			.get("/users/5")
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
			.get("/users/2")
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
			.get("/users/3")
		.then()
			.statusCode(200)
			.body("name", Matchers.containsString("Ana"))
			.body("filhos", Matchers.hasSize(2))
			.body("filhos[0].name", is("Zezinho"))
			.body("filhos[1].name", is("Luizinho"))
			.body("filhos.name", Matchers.hasItem("Zezinho"))
		;
	}
	
	// Lista Json na raiz
	@Test
	public void validarCampos5() {
		
		given()
		.when()
			.get("/users")
		.then()
			.statusCode(200)
			.body("$", Matchers.hasSize(3))
			.body("name", Matchers.hasItems("João da Silva", "Maria Joaquina", "Ana Júlia"))
			.body("age[0]", is(30))
			.body("filhos.name", Matchers.hasItem(Arrays.asList("Zezinho", "Luizinho")))
		;
	}
	
	

	
	@Test
	public void verifiacoesAvancadas() {
		
		given()
		.when()
			.get("/users")
		.then()
			.statusCode(200)
			.body("$", Matchers.hasSize(3))
			.body("age.findAll{it <= 25}.size()", is(2))
			.body("age.findAll{it <= 25 && it > 20}.size()", is(1))
			.body("findAll{it.age <= 25 && it.age > 20}.name", Matchers.hasItem("Maria Joaquina"))
			.body("findAll{it.age <= 25}[-1].name", is("Ana Júlia"))
			.body("find{it.age <= 25}.name", is("Maria Joaquina"))
			.body("findAll{it.name.contains('n')}.name", Matchers.hasItems("Maria Joaquina", "Ana Júlia"))
			.body("findAll{it.name.length()}.name", Matchers.hasItems("Maria Joaquina", "João da Silva"))
			.body("name.collect{it.toUpperCase()}", Matchers.hasItem("MARIA JOAQUINA"))
			.body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}", Matchers.hasItem("MARIA JOAQUINA"))
			.body("id.max()", is(3))
			.body("id.min()", is(1))
			.body("salary.findAll{it != null}.sum()", is(Matchers.closeTo(3734.5678f, 0.001)))
			.body("salary.findAll{it != null}.sum()", Matchers.allOf(Matchers.greaterThan(3000d), Matchers.lessThan(5000d)))
		;
	}
	
	@Test
	public void jsonPathComJava() {
		ArrayList<String> names =
		given()
		.when()
			.get("/users")
		.then()
			.statusCode(200)
			.extract().path("name.findAll{it.startsWith('Maria')}")
		;
		
		Assert.assertEquals(1, names.size());
		Assert.assertTrue(names.get(0).equalsIgnoreCase("mArIa JoaquinA"));
		Assert.assertEquals(names.get(0).toUpperCase(), "maria joaquina".toUpperCase());
	}
	
	
}
