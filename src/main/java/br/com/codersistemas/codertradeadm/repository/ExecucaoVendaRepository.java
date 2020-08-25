package br.com.codersistemas.codertradeadm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.codersistemas.codertradeadm.domain.ExecucaoVenda;
import br.com.codersistemas.codertradeadm.domain.Natureza;
import br.com.codersistemas.codertradeadm.domain.Ordem;

public interface ExecucaoVendaRepository extends JpaRepository<ExecucaoVenda, Long> {

	Optional<List<ExecucaoVenda>> findByCompraId(Long id);

	@Query(value="select ordem from Ordem ordem where ordem.ativo in (?1) and ordem.quantidadeDisponivel > 0 and ordem.natureza = ?2 order by ordem.atualizadoEm")
	List<Ordem> buscarOrdensCompraEmAberto(String[] ativos, Natureza natureza);
	
}