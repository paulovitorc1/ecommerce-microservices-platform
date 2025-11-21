package br.com.ecommerce.clientes.service;

import br.com.ecommerce.clientes.model.Cliente;
import br.com.ecommerce.clientes.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public Cliente salvar(Cliente cliente) {
        return repository.save(cliente);
    }

    public Optional<Cliente> obterPorCodigo(Long codigo) {
        return repository.findById(codigo);
    }

    public List<Cliente> obterTodos() {
        return repository.findAll();
    }

    public void deletarPorCodigo(Long codigo) {
        boolean codigoExiste = repository.existsById(codigo);
        if (!codigoExiste) {
            throw new EntityNotFoundException("Cliente não encontrado para o código: " + codigo);
        }
        repository.deleteById(codigo);
    }
}
