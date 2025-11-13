package br.com.ecommerce.clientes.repository;

import br.com.ecommerce.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
