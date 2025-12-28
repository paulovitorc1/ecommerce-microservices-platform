package br.com.ecommerce.pedidos.service;

import br.com.ecommerce.pedidos.client.ClientesClient;
import br.com.ecommerce.pedidos.client.ProdutosClient;
import br.com.ecommerce.pedidos.client.ServicoBancarioClient;
import br.com.ecommerce.pedidos.controller.dto.NovoPedidoDTO;
import br.com.ecommerce.pedidos.model.DadosPagamento;
import br.com.ecommerce.pedidos.model.ItemPedido;
import br.com.ecommerce.pedidos.model.Pedido;
import br.com.ecommerce.pedidos.model.enums.StatusPedido;
import br.com.ecommerce.pedidos.model.enums.TipoPagamento;
import br.com.ecommerce.pedidos.model.exception.ItemNaoEncontradoException;
import br.com.ecommerce.pedidos.publisher.PagamentoPublisher;
import br.com.ecommerce.pedidos.repository.ItemPedidoRepository;
import br.com.ecommerce.pedidos.repository.PedidoRepository;
import br.com.ecommerce.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServicoBancarioClient servicoBancarioClient;
    private final ClientesClient apiClientes;
    private final ProdutosClient apiProdutos;
    private final PagamentoPublisher pagamentoPublisher;

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

    public void atualizarStatusPagamento(Long codigoPedido, String chavePagamento, boolean sucesso, String observacoes) {
        var pedidoEncontrado = repository.findByCodigoAndChavePagamento(codigoPedido, chavePagamento);

        if (pedidoEncontrado.isEmpty()) {
            var msg = String.format("Pedido não encontrado para o código %d e chave de pagamento %s", codigoPedido, chavePagamento);
            log.error(msg);
            return;
        }

        Pedido pedido = pedidoEncontrado.get();

        if (sucesso) {
            prepararEPublicarPedidoPago(pedido);
        } else {
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }
        repository.save(pedido);
    }

    private void prepararEPublicarPedidoPago(Pedido pedido) {
        pedido.setStatus(StatusPedido.PAGO);
        carregarDadosCliente(pedido);
        carregarItensPedido(pedido);
        pagamentoPublisher.pubicar(pedido);
    }

    @Transactional
    public void adicionarNovoPagamento(Long codigoPedido, String dadosCartao, TipoPagamento tipo) {

        var pedidoEncontrado = repository.findById(codigoPedido);

        if (pedidoEncontrado.isEmpty()) {
            throw new ItemNaoEncontradoException("Pedido não encontrado para o código informado.");
        }

        var pedido = pedidoEncontrado.get();

        DadosPagamento dadosPagamento = new DadosPagamento();
        dadosPagamento.setTipoPagamento(tipo);
        dadosPagamento.setDados(dadosCartao);

        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo pagamento realizado. Aguardando processamento...");

        String novaChavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(novaChavePagamento);

        repository.save(pedido);
    }

    public Optional<Pedido> carregarDadosCompletosPedido(Long codigo){
        Optional<Pedido> pedido = repository.findById(codigo);
        pedido.ifPresent(this::carregarDadosCliente);
        pedido.ifPresent(this::carregarItensPedido);
        return pedido;
    }

    public void carregarDadosCliente(Pedido pedido){
        Long codigoCliente = pedido.getCodigoCliente();
        var response = apiClientes.obterPorCodigo(codigoCliente);
        pedido.setDadosCliente(response.getBody());
    }

    public void carregarItensPedido(Pedido pedido){
        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedido);
        pedido.setItens(itens);
        pedido.getItens().forEach(this::carregarDadosProduto);
    }

    public void carregarDadosProduto(ItemPedido item){
        Long codigoProduto = item.getCodigoProduto();
        var response = apiProdutos.obterPorCodigo(codigoProduto);
        item.setNome(response.getBody().nome());
    }

}
