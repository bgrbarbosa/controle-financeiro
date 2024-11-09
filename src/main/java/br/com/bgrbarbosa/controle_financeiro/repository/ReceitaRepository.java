package br.com.bgrbarbosa.controle_financeiro.repository;

import br.com.bgrbarbosa.controle_financeiro.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}
