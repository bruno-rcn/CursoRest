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

}
