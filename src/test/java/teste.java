import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class teste {

	public static void main(String[] args) {
		
		Response request = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");
		
		System.out.println(request.getBody().asString());
	}
}
