package br.com.ecommerce.pedidos.repository;

import br.com.ecommerce.pedidos.model.ItemPedido;
import br.com.ecommerce.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedido(Pedido pedido);
}
