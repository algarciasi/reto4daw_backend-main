package empleos.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
public class DataUserConfiguration {

	//Ejemplo sin seguridad con permitAll
	/*@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // Desactiva CSRF para pruebas
				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Permite todas las peticiones
				.formLogin(login -> login.disable()) // Desactiva formulario de login
				.httpBasic(basic -> basic.disable()); // Desactiva autenticación básica
		
		return http.build();
	}*/
	
	
	// Ejemplo con seguridad basica
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable()) // Solo en desarrollo; en prod deberías activarlo con protección adecuada
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos
                .requestMatchers("/auth/login", "/usuarios/registro", "/categorias/todas", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // Cliente
                .requestMatchers("/usuarios/seguimiento/**", "/usuarios/cancelar/**", "solicitudes/nueva", "/solicitudes/usuario").hasRole("CLIENTE")
                
                
                // Empresa y Cliente
                .requestMatchers("/vacantes/todas", "/vacantes/buscar", "/solicitudes/cancelar/**", "/solicitudes/empresa").hasAnyRole("CLIENTE", "EMPRESA")
                
                // Empresa
                .requestMatchers("/vacantes/**", "/solicitudes/**", "/empresas/asignar/**", "/empresas/buscar-por-email/**").hasRole("EMPRESA")
                
                // Empresa y ADMON
                .requestMatchers("/empresas/editar/**").hasAnyRole("EMPRESA", "ADMON")
                
                // Empresa y ADMON (ver/buscar su empresa por ID) 13/04
                .requestMatchers("/empresas/buscar/**").hasAnyRole("EMPRESA", "ADMON")
                
                // Admin
                .requestMatchers("/empresas/**", "/categorias/**", "/usuarios/admin/**", "/usuarios/baja/**").hasRole("ADMON")

                // Todo lo demás requiere login
                .anyRequest().authenticated()
            )
            //.formLogin(form -> form.permitAll()) // Login web
            .formLogin(form -> form.disable()) // ***********editado 09/04***********
            .httpBasic(Customizer.withDefaults()); // Auth básico Postman

        return http.build();
    }

    // Codificador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configura el AuthenticationManager que Spring necesita para autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Configuration
    public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**") // habilita para todas las rutas
                    .allowedOrigins("https://algarciasi.com", "http://localhost:4200") // origen del frontend
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
        }
    }



}