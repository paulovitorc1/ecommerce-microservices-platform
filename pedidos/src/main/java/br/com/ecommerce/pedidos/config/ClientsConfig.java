package br.com.ecommerce.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "br.com.ecommerce.pedidos.client")
public class ClientsConfig {
}
