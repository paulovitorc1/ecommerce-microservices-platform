package br.com.ecommerce.produtos.repository;

import br.com.ecommerce.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
