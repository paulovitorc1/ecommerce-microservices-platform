package br.com.ecommerce.produtos.service;

import br.com.ecommerce.produtos.model.Produto;
import br.com.ecommerce.produtos.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public Produto atualizarPorCodigo(Long codigo, Produto atualizado) {
        return repository.findById(codigo)
                .map(produtoExistente -> {

                    // Atualiza os campos desejados
                    produtoExistente.setNome(atualizado.getNome());
                    produtoExistente.setValorUnitario(atualizado.getValorUnitario());
                    return repository.save(produtoExistente);
                })
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado: " + codigo));
    }


    public void deletar(Produto produto) {
        produto.setAtivo(false);
        repository.save(produto);
    }
}
