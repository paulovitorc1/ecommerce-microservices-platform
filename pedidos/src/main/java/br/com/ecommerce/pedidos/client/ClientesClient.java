package br.com.ecommerce.pedidos.client;

import br.com.ecommerce.pedidos.client.representation.ClienteRepresentation;
import br.com.ecommerce.pedidos.client.representation.ProdutoRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientes", url = "${ecommerce.pedidos.clients.clientes.url}")
public interface ClientesClient {

    @GetMapping("{codigo}")
    ResponseEntity<ClienteRepresentation> obterPorCodigo(@PathVariable("codigo") Long codigo);

}
