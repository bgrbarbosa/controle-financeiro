package br.com.bgrbarbosa.controle_financeiro.repository;


import br.com.bgrbarbosa.controle_financeiro.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
}
