package br.com.bgrbarbosa.controle_financeiro.repository;

import br.com.bgrbarbosa.controle_financeiro.model.LancamentoDespesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LancamentoDespesaRepository extends JpaRepository<LancamentoDespesa, Long> {

    @Query(value = "SELECT * FROM TB_LANC_DESPESA ld WHERE ld.dt_venc BETWEEN :dt_init AND :dt_final", nativeQuery = true)
    List<LancamentoDespesa> findByPeriod(LocalDate dt_init, LocalDate dt_final);
}
