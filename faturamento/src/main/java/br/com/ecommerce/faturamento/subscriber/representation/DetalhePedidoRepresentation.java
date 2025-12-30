package br.com.ecommerce.faturamento.subscriber.representation;

import br.com.ecommerce.faturamento.publisher.representation.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public record DetalhePedidoRepresentation(
        Long codigo,
        Long codigoCliente,
        String nome,
        String cpf,
        String logradouro,
        String numero,
        String bairro,
        String email,
        String telefone,
        String dataPedido,
        BigDecimal total,
        StatusPedido status,
        String urlNf,
        String codigoRastreio,
        List<DetalheItemPedidoRepresentation> itens
        ) {
}
