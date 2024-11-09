package br.com.bgrbarbosa.controle_financeiro.repository;

import br.com.bgrbarbosa.controle_financeiro.model.LancamentoReceita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoReceitaRepository extends JpaRepository<LancamentoReceita, Long> {
    @Query(value = "SELECT * FROM TB_LANC_RECEITA ld WHERE ld.dt_venc = :dt_venc", nativeQuery = true)
    List<LancamentoReceita> findByDate(LocalDate dt_venc);

    @Query(value = "SELECT * FROM TB_LANC_RECEITA ld WHERE ld.dt_venc BETWEEN :dt_init AND :dt_final", nativeQuery = true)
    List<LancamentoReceita> findByPeriod(LocalDate dt_init, LocalDate dt_final);

    @Query(value = "SELECT * FROM TB_LANC_RECEITA ld WHERE ld.dt_venc BETWEEN :dt_init AND :dt_final AND ld.status = :status", nativeQuery = true)
    List<LancamentoReceita> findByStatus(LocalDate dt_init, LocalDate dt_final, String status);

}
