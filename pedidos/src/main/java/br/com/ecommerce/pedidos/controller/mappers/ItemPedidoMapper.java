package br.com.ecommerce.pedidos.controller.mappers;

import br.com.ecommerce.pedidos.controller.dto.ItemPedidoDTO;
import br.com.ecommerce.pedidos.controller.dto.NovoPedidoDTO;
import br.com.ecommerce.pedidos.model.ItemPedido;
import br.com.ecommerce.pedidos.model.Pedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {
    ItemPedido map(ItemPedidoDTO dto);
}
