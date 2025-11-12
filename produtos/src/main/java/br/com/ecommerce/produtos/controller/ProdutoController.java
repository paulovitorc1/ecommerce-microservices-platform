package br.com.ecommerce.produtos.controller;

import br.com.ecommerce.produtos.model.Produto;
import br.com.ecommerce.produtos.service.ProdutoService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto){
        service.salvar(produto);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("{codigo}")
    public ResponseEntity<Produto> obterPorCodigo(@PathVariable("codigo") Long codigo) {
        return service
                .obterPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



}

