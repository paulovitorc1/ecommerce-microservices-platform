package br.com.ecommerce.pedidos.controller.dto;

import br.com.ecommerce.pedidos.model.enums.TipoPagamento;

public record AdicionarNovoPagamentoDTO(Long codigoPedido, String dados, TipoPagamento tipoPagamento) {
}
