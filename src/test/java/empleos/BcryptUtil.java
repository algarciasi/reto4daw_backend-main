package empleos;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptUtil {

	public static void main(String[] args) {
	    String raw = "adminpass";
	    String hash = new BCryptPasswordEncoder().encode(raw);
	    System.out.println("Hash generado: " + hash);
	    System.out.println("Verificación: " + new BCryptPasswordEncoder().matches("adminpass", hash));
	}

}
