package br.com.ecommerce.pedidos.controller.dto;

import br.com.ecommerce.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDTO (String dados, TipoPagamento tipoPagamento) {
}
