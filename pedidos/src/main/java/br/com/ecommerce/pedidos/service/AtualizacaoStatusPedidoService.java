package br.com.ecommerce.pedidos.service;

import br.com.ecommerce.pedidos.model.enums.StatusPedido;
import br.com.ecommerce.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizacaoStatusPedidoService {

    private final PedidoRepository repository;

    @Transactional
    public void atualizarStatus(Long codigo, StatusPedido status, String urlNotaFiscal, String codigoRastreio){
        repository.findById(codigo).ifPresent(pedido -> {
            pedido.setStatus(status);
            pedido.setUrlNf(urlNotaFiscal);
            pedido.setCodigoRastreio(codigoRastreio);
        });
    }
}
