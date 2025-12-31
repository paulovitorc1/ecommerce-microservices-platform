package br.com.ecommerce.logistica.model;

public record AtualizacaoEnvioPedido(Long codigo, StatusPedido status, String codigoRastreio) {
}
