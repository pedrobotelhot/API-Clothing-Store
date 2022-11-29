package br.Store.Clothing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.Store.Clothing.models.Pedido;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
