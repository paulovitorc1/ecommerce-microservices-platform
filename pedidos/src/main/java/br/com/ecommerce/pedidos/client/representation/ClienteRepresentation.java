package br.com.ecommerce.pedidos.client.representation;

import java.math.BigDecimal;

public record ClienteRepresentation(
        Long codigo,
        String nome,
        String cpf,
        String logradouro,
        String numero,
        String bairro,
        String telefone) {
}
