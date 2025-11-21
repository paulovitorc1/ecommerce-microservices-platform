package br.com.ecommerce.clientes.controller;

import br.com.ecommerce.clientes.model.Cliente;
import br.com.ecommerce.clientes.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente){
        service.salvar(cliente);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("{codigo}")
    public ResponseEntity<Cliente> obterPorCodigo(@PathVariable("codigo") Long codigo) {
        return service
                .obterPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> obterTodos() {
        var clientes = service.obterTodos();
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Não há itens para listar.");
        }
        return ResponseEntity.ok(clientes);
    }

    @DeleteMapping("{codigo}")
    public ResponseEntity<?> deletarPorCodigo(@PathVariable Long codigo) {
        try {
            service.deletarPorCodigo(codigo);
            return ResponseEntity.ok("Cliente deletado com sucesso. ID: " + codigo);
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

}

