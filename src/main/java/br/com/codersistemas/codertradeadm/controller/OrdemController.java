package br.com.codersistemas.codertradeadm.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.codersistemas.codertradeadm.domain.Ordem;
import br.com.codersistemas.codertradeadm.repository.OrdemRepository;
import br.com.codersistemas.codertradeadm.service.ArquivoOrdensService;
import br.com.codersistemas.libs.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ordens")
public class OrdemController {

	@Autowired
	private OrdemRepository arquivoOrdensRepository;
	
	@Autowired
	private ArquivoOrdensService service;

	@GetMapping
	public List<Ordem> listar() {
		log.debug("listar!");
		List<Ordem> findAll = arquivoOrdensRepository.findAll(Sort.by(Order.asc("nome")));
		findAll.forEach(obj -> {
			ReflectionUtils.mapToBasicDTO(obj);
		});
		return findAll;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Ordem> buscar(@PathVariable Long id) {
		Optional<Ordem> findById = arquivoOrdensRepository.findById(id);
		if (!findById.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(findById.get());
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Ordem adicionar(@Valid @RequestBody Ordem entity) {
		return arquivoOrdensRepository.save(entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Ordem> excluir(@PathVariable Long id) {
		Optional<Ordem> findById = arquivoOrdensRepository.findById(id);
		if (!findById.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		arquivoOrdensRepository.delete(findById.get());
		return new ResponseEntity<Ordem>(HttpStatus.NO_CONTENT);
	}

	/**
	 * http://localhost:8080/ordens/importar-arquivos
	 * 
	 * @return
	 */
	@GetMapping("/importar-arquivos")
	public ResponseEntity<Ordem> importarArquivos() {
		log.info(">>>>>");
		List<Ordem> importarArquivos = service.importarArquivos();
		service.calcular(importarArquivos);
		log.info(">>>>> {} arquivos", importarArquivos.size());
		return new ResponseEntity<Ordem>(HttpStatus.NO_CONTENT);
	}

}
