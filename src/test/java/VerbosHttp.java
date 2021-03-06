import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
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
			.body("error", is("Name ? um atributo obrigat?rio"))
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
	
	@Test
	public void deletarUsuario() {
		
		given()
		.when()
			.delete("https://restapi.wcaquino.me/users/1")
		.then()
			.statusCode(204)
		;
	}
	
	@Test
	public void usuarioInexistente() {
		
		given()
		.when()
			.delete("https://restapi.wcaquino.me/users/1000")
		.then()
			.statusCode(400)
			.body("error", is("Registro inexistente"))
		;
	}
	
	@Test
	public void salvarUsuarioComMap() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "Usuario via map");
		params.put("age", 25);
		
		given()
			.contentType("application/json")
			.body(params)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.statusCode(201)
			.body("id", is(Matchers.notNullValue()))
			.body("name", is("Usuario via map"))
			.body("age", is(25))
		;
	}
	
	@Test
	public void salvarUsuarioComObjeto() {
		Usuarios user = new Usuarios("Usuario via objeto", 35);
		
		given()
			.contentType("application/json")
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.statusCode(201)
			.body("id", is(Matchers.notNullValue()))
			.body("name", is("Usuario via objeto"))
			.body("age", is(35))
		;
	}
	
	@Test
	public void salvarUsuarioComObjetoJunit() {
		Usuarios user = new Usuarios("Usuario deserializado", 35);
		
		Usuarios usuarioInserido = given()
			.contentType("application/json")
			.body(user)
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.statusCode(201)
			.extract().body().as(Usuarios.class)
		;
		
		System.out.println(usuarioInserido);
		Assert.assertEquals("Usuario deserializado", usuarioInserido.getName());
	}

}
