package br.com.codersistemas.codertradeadm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codersistemas.codertradeadm.domain.Ordem;

public interface OrdemRepository extends JpaRepository<Ordem, Long> {

	Ordem findByCodigo(Long long1);
	
}
