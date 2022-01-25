import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.junit.Test;

public class VerbosHttp {
	
	@Test
	public void salvarUsuario() {
		
		given()
			.body("{\"name\": \"Jose\", \"age\": 50}")
			.contentType("application/json")
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.statusCode(201)
			.body("id", is(Matchers.notNullValue()))
			.body("name", is("Jose"))
			.body("age", is(50))
		;
	}
	
	@Test
	public void naoDeveSalvarUsuarioSemNome() {
		
		given()
			.contentType("application/json")
			.body("{\"age\": 50}")
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.statusCode(400)
			.body("id", is(Matchers.nullValue()))
			.body("error", is("Name é um atributo obrigatório"))
		;
	}
	
	@Test
	public void deveAlterarUsuario() {
		
		given()
			.body("{\"name\": \"Usuario alterado\", \"age\": 80}")
			.contentType("application/json")
		.when()
			.put("https://restapi.wcaquino.me/users/1")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario alterado"))
			.body("age", is(80))
		;
	}
	
	@Test
	public void urlParametrizada() {
		
		given()
			.body("{\"name\": \"Usuario alterado\", \"age\": 80}")
			.contentType("application/json")
		.when()
			.put("https://restapi.wcaquino.me/{entidade}/{userId}", "users", "1")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario alterado"))
			.body("age", is(80))
		;
	}
	
	@Test
	public void urlParametrizada2() {
		
		given()
			.body("{\"name\": \"Usuario alterado\", \"age\": 80}")
			.contentType("application/json")
			.pathParam("entidade", "users")
			.pathParam("userId", 1)
		.when()
			.put("https://restapi.wcaquino.me/{entidade}/{userId}")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario alterado"))
			.body("age", is(80))
		;
	}

}
