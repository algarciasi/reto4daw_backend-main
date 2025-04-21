package empleos.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import empleos.modelo.entity.Usuarios;
import empleos.modelo.repository.UsuariosRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuariosRepository usuariosRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuarios usuario = usuariosRepo.findById(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        String rawPassword = usuario.getPassword();

        // Comprobar si el pass esta cifrado
        if (!rawPassword.startsWith("$2a$") && !rawPassword.startsWith("$2b$") && !rawPassword.startsWith("$2y$")) {
            System.out.println("Encriptando password para usuario: " + usuario.getEmail());

            String encrypted = passwordEncoder.encode(rawPassword);
            usuario.setPassword(encrypted);
            usuariosRepo.save(usuario);

            System.out.println("Password encriptada y actualizada en BBDD");
        }

        System.out.println("Usuario autenticado: " + usuario.getEmail() + " | Rol: " + usuario.getRol());

        return org.springframework.security.core.userdetails.User.builder()
            .username(usuario.getEmail())
            .password(usuario.getPassword()) // ya actualizado 
            .roles(usuario.getRol()) // Admin, Empresa o Cliente
            .disabled(usuario.getEnabled() == 0)
            .build();
    }
}