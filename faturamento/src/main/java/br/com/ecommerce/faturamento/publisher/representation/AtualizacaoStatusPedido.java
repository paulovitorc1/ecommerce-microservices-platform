package br.com.ecommerce.faturamento.publisher.representation;

public record AtualizacaoStatusPedido(Long codigo, StatusPedido status, String urlNotaFiscal) {
}
