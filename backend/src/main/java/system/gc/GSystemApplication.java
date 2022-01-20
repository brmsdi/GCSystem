package system.gc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class GSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(GSystemApplication.class, args);
	}

}