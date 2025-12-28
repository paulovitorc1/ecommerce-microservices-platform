package br.com.ecommerce.pedidos.controller;

import br.com.ecommerce.pedidos.controller.dto.AdicionarNovoPagamentoDTO;
import br.com.ecommerce.pedidos.controller.dto.NovoPedidoDTO;
import br.com.ecommerce.pedidos.controller.dto.PedidoDTO;
import br.com.ecommerce.pedidos.controller.mappers.PedidoMapper;
import br.com.ecommerce.pedidos.model.ErroResposta;
import br.com.ecommerce.pedidos.model.Pedido;
import br.com.ecommerce.pedidos.model.exception.ItemNaoEncontradoException;
import br.com.ecommerce.pedidos.model.exception.ValidationException;
import br.com.ecommerce.pedidos.publisher.DetalhePedidoMapper;
import br.com.ecommerce.pedidos.publisher.representation.DetalhePedidoRepresentation;
import br.com.ecommerce.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;
    private final DetalhePedidoMapper detalhePedidoMapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO dto) {
        try {
            var pedido = mapper.map(dto);
            var novoPedido = service.criar(pedido);
            return ResponseEntity.ok(novoPedido.getCodigo());
        } catch (ValidationException e) {
            var erro = new ErroResposta("Erro de validação.", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @PostMapping("pagamentos")
    public ResponseEntity<Object> adiconarNovoPagamento(@RequestBody AdicionarNovoPagamentoDTO dto) {
        try {
            service.adicionarNovoPagamento(dto.codigoPedido(), dto.dados(), dto.tipoPagamento());
            return ResponseEntity.noContent().build();
        } catch (ItemNaoEncontradoException e) {
            var erro = new ErroResposta("Item não encontrado.", "codigoPedido", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @GetMapping("{codigo}")
    public ResponseEntity<DetalhePedidoRepresentation> obterDetalhesPedido(@PathVariable("codigo") Long codigo) {
        return service
                .carregarDadosCompletosPedido(codigo)
                .map(detalhePedidoMapper::map)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

}
