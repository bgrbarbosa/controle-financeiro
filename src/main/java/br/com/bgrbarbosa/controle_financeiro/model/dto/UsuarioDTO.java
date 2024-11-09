package br.com.bgrbarbosa.controle_financeiro.model.dto;



import br.com.bgrbarbosa.controle_financeiro.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.validation.constraints.Email;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Long id;

    @Email(message = "O usuário deve um email válido")
    private String username;

    private String password;

    public User toEntity(UsuarioDTO dto){
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.username);
        user.setPassword(dto.getPassword());
        return user;
    }

    public UsuarioDTO toDTO(User user){
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        return dto;
    }
}
