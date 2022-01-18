import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class teste {

	public static void main(String[] args) {
		
		Response request = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");
		
		System.out.println(request.getBody().asString().equals("Ola Mundo!"));
		System.out.println(request.statusCode() == 200);
		System.out.println("---------------------------------------------------");
		System.out.println(request.getBody().asString().equals("Oa Mundo!"));
		System.out.println(request.statusCode() == 20);
		
		ValidatableResponse validacao = request.then();
		validacao.statusCode(200);
	}
}
