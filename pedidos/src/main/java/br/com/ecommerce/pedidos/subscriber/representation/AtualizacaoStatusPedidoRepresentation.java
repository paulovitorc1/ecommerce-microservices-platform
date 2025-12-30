package br.com.ecommerce.pedidos.subscriber.representation;

import br.com.ecommerce.pedidos.model.enums.StatusPedido;

public record AtualizacaoStatusPedidoRepresentation(
        Long codigo,
        StatusPedido status,
        String urlNotaFiscal,
        String codigoRastreio) {
}
