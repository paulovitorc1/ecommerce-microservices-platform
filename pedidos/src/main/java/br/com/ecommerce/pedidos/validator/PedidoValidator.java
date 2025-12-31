package br.com.ecommerce.pedidos.validator;

import br.com.ecommerce.pedidos.client.ClientesClient;
import br.com.ecommerce.pedidos.client.ProdutosClient;
import br.com.ecommerce.pedidos.client.representation.ClienteRepresentation;
import br.com.ecommerce.pedidos.client.representation.ProdutoRepresentation;
import br.com.ecommerce.pedidos.model.ItemPedido;
import br.com.ecommerce.pedidos.model.Pedido;
import br.com.ecommerce.pedidos.model.exception.ValidationException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoValidator {
    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validar(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigoCliente) {
        try {
            var response = clientesClient.obterPorCodigo(codigoCliente);
            ClienteRepresentation cliente = response.getBody();
            log.info("Cliente de código {} encontrado: {}", cliente.codigo(), cliente.nome());

            if (!cliente.ativo()) {
                throw new ValidationException("codigoCliente", "Cliente inativo.");
            }

        } catch (FeignException.NotFound e) {
            var message = String.format("Cliente de código %d não encontrado.", codigoCliente);
            throw new ValidationException("codigoCliente", message);
        }
    }

    private void validarItem(ItemPedido item) {
        try {
            var response = produtosClient.obterPorCodigo(item.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();
            log.info("Produto de código {} encontrado: {}", produto.codigo(), produto.nome());

            if (!produto.ativo()) {
                throw new ValidationException("codigoProduto", "Produto inativo.");
            }

        } catch (FeignException.NotFound e) {
            var message = String.format("Produto de código %d não encontrado.", item.getCodigoProduto());
            throw new ValidationException("codigoProduto", message);
        }
    }

}

