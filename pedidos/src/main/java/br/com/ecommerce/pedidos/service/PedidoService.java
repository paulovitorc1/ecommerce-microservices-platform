package br.com.ecommerce.pedidos.service;

import br.com.ecommerce.pedidos.client.ServicoBancarioClient;
import br.com.ecommerce.pedidos.controller.dto.NovoPedidoDTO;
import br.com.ecommerce.pedidos.model.ItemPedido;
import br.com.ecommerce.pedidos.model.Pedido;
import br.com.ecommerce.pedidos.repository.ItemPedidoRepository;
import br.com.ecommerce.pedidos.repository.PedidoRepository;
import br.com.ecommerce.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServicoBancarioClient servicoBancarioClient;

    @Transactional
    public Pedido criar(Pedido pedido) {
        validator.validar(pedido);
        realizarPersistencia(pedido);
        enviarSolicitacaoPagamento(pedido);
        return pedido;
    }

    private void realizarPersistencia(Pedido pedido) {
        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {
        var chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);
    }


}
