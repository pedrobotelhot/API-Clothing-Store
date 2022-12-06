package br.Store.Clothing.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.Store.Clothing.models.Cliente;
import br.Store.Clothing.repository.ClienteRepository;
import br.Store.Clothing.services.ClienteService;


@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClienteController {
	
	@Autowired
	private ClienteRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> findAllByCliente() {
		
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> findByIdCliente(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/logar")
	public ResponseEntity<ClienteLogin> Autentication(@RequestBody Optional<ClienteLogin> user) {
		return ClienteService.Logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Cliente> Post(@RequestBody Cliente usuario) {
		Optional<Cliente> user = ClienteService.CadastrarCliente(usuario);

		try {
			return ResponseEntity.ok(user.get());

		}catch(Exception e) {
			return ResponseEntity.badRequest().build();

		}

	}

	@PutMapping
	public ResponseEntity<Cliente> putCliente(@RequestBody Cliente cliente) {
		
		return ResponseEntity.ok(repository.save(cliente));
	}
	
	@DeleteMapping("/{id}")
	public void deletaCliente(@PathVariable long id) {
		
		repository.deleteById(id);
	}

}
