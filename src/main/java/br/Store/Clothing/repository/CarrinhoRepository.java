package br.Store.Clothing.repository;

import br.Store.Clothing.models.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
}
