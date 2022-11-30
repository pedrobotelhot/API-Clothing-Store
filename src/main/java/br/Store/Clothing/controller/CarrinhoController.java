package br.Store.Clothing.controller;

import br.Store.Clothing.models.Carrinho;
import br.Store.Clothing.repository.CarrinhoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class CarrinhoController {

    @Autowired
    private CarrinhoRepository repository;


    @GetMapping
    public ResponseEntity<List<Carrinho>> findAllByCarrinhos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrinho> findByIdCarrinho(@PathVariable long id) {
        return repository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Carrinho> postCarrinho(@RequestBody Carrinho carrinho) {

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(carrinho));
    }

    @PutMapping
    public ResponseEntity<Carrinho> putCarrinho(@RequestBody Carrinho carrinho) {

        return ResponseEntity.ok(repository.save(carrinho));
    }

    @DeleteMapping("/{id}")
    public void deleteCarrinho(@PathVariable long id) {

        repository.deleteById(id);
    }

}
