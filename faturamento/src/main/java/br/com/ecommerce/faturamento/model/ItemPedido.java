package br.com.ecommerce.faturamento.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ItemPedido {
    private Long codigo;
    private String nome;
    private BigDecimal valorUnitario;
    private Integer quantidade;
    private BigDecimal total;
}
