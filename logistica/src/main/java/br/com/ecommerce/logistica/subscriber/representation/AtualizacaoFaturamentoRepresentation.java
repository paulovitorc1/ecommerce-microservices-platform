package br.com.ecommerce.logistica.subscriber.representation;

import br.com.ecommerce.logistica.model.StatusPedido;

public record AtualizacaoFaturamentoRepresentation(Long codigo, StatusPedido status, String urlNotaFiscal) {
}
