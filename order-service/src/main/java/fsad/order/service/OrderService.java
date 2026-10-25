package fsad.order.service;

import fsad.order.client.ProductClient;
import fsad.order.dto.CreateOrderRequest;
import fsad.order.model.Order;
import fsad.order.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductClient productClient;

    public OrderService(OrderRepository repository, ProductClient productClient) {
        this.repository = repository;
        this.productClient = productClient;
    }

    @Transactional
    public Order create(CreateOrderRequest request) {
        // Keep it simple: block here in sync service; for production use reactive end-to-end.
        ProductClient.ProductPayload product = productClient.getProductById(request.getProductId()).block();
        BigDecimal total = product.price().multiply(BigDecimal.valueOf(request.getQuantity()));

        Order order = new Order();
        order.setProductId(product.id());
        order.setProductSku(product.sku());
        order.setProductName(product.name());
        order.setProductPrice(product.price());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(total);
        return repository.save(order);

    }

    public java.util.List<Order> getAll() {
        return repository.findAll();
    }

    public Order getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new fsad.order.web.ResourceNotFoundException("Order not found: " + id));
    }
}

