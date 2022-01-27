import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.hamcrest.Matchers;
import org.junit.Test;


public class FileTest {
	
	@Test
	public void deveFazeUploadArquivo() {
		given()
			.multiPart("arquivo", new File("C:\\Projects\\CursoRest\\src\\test\\resources\\cv.pdf"))
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.statusCode(200)
			.body("name", Matchers.is("cv.pdf"))
		;
	}
	
	@Test
	public void deveFazeDownloadArquivo() throws IOException {
		byte[] image = given()
		.when()
			.get("https://restapi.wcaquino.me/download")
		.then()
			.statusCode(200)
			.extract().asByteArray()
		;
		File imagem = new File("C:\\Projects\\CursoRest\\src\\test\\resources\\file.jpg");
		OutputStream out = new FileOutputStream(imagem);
		out.write(image);
		out.close();
		
		System.out.println(image.length);
	}

}
