package br.com.bgrbarbosa.controle_financeiro.controller;

import br.com.bgrbarbosa.controle_financeiro.model.User;
import br.com.bgrbarbosa.controle_financeiro.model.dto.UsuarioDTO;
import br.com.bgrbarbosa.controle_financeiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/usuario")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    UsuarioDTO usuarioDTO = new UsuarioDTO();
    UsuarioDTO usuarioSelecionado = new UsuarioDTO();

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @GetMapping("")
    public ModelAndView index(Principal principal) {
        User usuarioLogado = userRepository.findByUsername(principal.getName());
        List<User> usuarios = userRepository.findAll();
        ModelAndView mv = new ModelAndView("usuario/index");
        mv.addObject("usuarios", usuarios);
        mv.addObject("usuarioLogado", usuarioLogado);
        mv.addObject("usuarioDTO", new UsuarioDTO()); // Adiciona um novo DTO vazio
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastro() {
        List<User> usuarios = userRepository.findAll();
        ModelAndView mv = new ModelAndView("usuario/cadastro");
        mv.addObject("usuarioDTO", this.usuarioDTO);
        return mv;
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastro(@Valid UsuarioDTO dto, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("usuario/cadastro");
        if (bindingResult.hasErrors()) {
            mv.addObject("usuarios", userRepository.findAll());
            mv.addObject("usuarioDTO", dto);
            return mv;
        } else {
            User user = new User();
            user.setId(dto.getId());
            user.setUsername(dto.getUsername());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            this.userRepository.save(user);
            return new ModelAndView("redirect:/usuario");
        }
    }
    @GetMapping("/edicao/{id}")
    public ModelAndView atualizar(@PathVariable Long id) {
        Optional<User> aux = userRepository.findById(id);
        if (aux.isPresent()) {
            this.usuarioSelecionado = this.usuarioSelecionado.toDTO(aux.get());
            ModelAndView mv = new ModelAndView("usuario/edicao");
            mv.addObject("usuarioSelecionado", this.usuarioSelecionado.toDTO(aux.get()));
            return mv;
        } else {
            return new ModelAndView("redirect:/usuario");
        }
    }


    @PostMapping("/edicao/{id}")
    public ModelAndView atualizar(@PathVariable Long id, @Valid UsuarioDTO dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("usuario/edicao");
            this.usuarioSelecionado.setId(dto.getId());
            this.usuarioSelecionado.setUsername(dto.getUsername());
            this.usuarioSelecionado.setPassword(passwordEncoder.encode(dto.getPassword()));
            mv.addObject("usuarioSelecionado", this.usuarioSelecionado);
            mv.addObject("hasErrors", true);
            return mv;
        } else {
            Optional<User> optional = this.userRepository.findById(id);
            User usuario = new User();
            if (optional.isPresent()) {
                usuario.setId(dto.getId());
                usuario.setUsername(dto.getUsername());
                usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
                userRepository.save(usuario);
                return new ModelAndView("redirect:/usuario");
            } else {
                return new ModelAndView("redirect:/usuario"); // Se id não for encontrado
            }
        }
    }

    @GetMapping("/{id}")
    public ModelAndView delete(@PathVariable Long id, Principal principal) {
        User usuarioLogado = userRepository.findByUsername(principal.getName());

        if (!usuarioLogado.getId().equals(id)) {
            try {
                this.userRepository.deleteById(id);
            } catch (EmptyResultDataAccessException e) {
                System.out.println(e);
                // Tratar erro
            }
        }
        return new ModelAndView("redirect:/usuario");
    }
}
