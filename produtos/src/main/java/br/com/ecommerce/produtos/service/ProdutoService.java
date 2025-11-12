package br.com.ecommerce.produtos.service;

import br.com.ecommerce.produtos.model.Produto;
import br.com.ecommerce.produtos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public Optional<Produto> obterPorCodigo(Long codigo) {
        return repository.findById(codigo);
    }
}
