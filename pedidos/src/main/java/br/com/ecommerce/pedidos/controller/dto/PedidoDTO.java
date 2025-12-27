package br.com.ecommerce.pedidos.controller.dto;

import br.com.ecommerce.pedidos.model.ItemPedido;
import br.com.ecommerce.pedidos.model.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDTO(Long codigoCliente, LocalDateTime dataPedido, String observacoes, StatusPedido status, BigDecimal total, String urlNf,
                        List<ItemPedido> itens) {
}
