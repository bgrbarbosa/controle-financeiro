package br.com.bgrbarbosa.controle_financeiro.repository;

import br.com.bgrbarbosa.controle_financeiro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
