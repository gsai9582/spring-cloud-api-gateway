package fsad.order.client;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(@LoadBalanced WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://PRODUCT-SERVICE").build();
    }

    public Mono<ProductPayload> getProductById(Long productId) {
        return webClient.get()
                .uri("/products/{id}", productId)
                .retrieve()
                .bodyToMono(ProductPayload.class);
    }

    public record ProductPayload(Long id, String sku, String name, java.math.BigDecimal price) {
    }
}


