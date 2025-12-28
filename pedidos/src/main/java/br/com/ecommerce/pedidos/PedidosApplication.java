package br.com.ecommerce.pedidos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class PedidosApplication {

    @Bean
    public CommandLineRunner commandLineRunner(KafkaTemplate<String, String> template) {
        return args -> template.send("ecommerce.pedidos-pagos", "dados", "Pedido com pagamento realizado.");
    }

	public static void main(String[] args) {
		SpringApplication.run(PedidosApplication.class, args);
	}

}
