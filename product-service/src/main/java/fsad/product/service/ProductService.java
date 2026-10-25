package fsad.product.service;

import fsad.product.dto.ProductCreateRequest;
import fsad.product.dto.ProductUpdateRequest;
import fsad.product.model.Product;
import fsad.product.repo.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new fsad.product.web.ResourceNotFoundException("Product not found: " + id));
    }

    @Transactional
    public Product create(ProductCreateRequest req) {
        repository.findBySku(req.getSku()).ifPresent(p -> {
            throw new fsad.product.web.ConflictException("SKU already exists: " + req.getSku());
        });
        return repository.save(new Product(req.getSku(), req.getName(), req.getPrice()));
    }

    @Transactional
    public Product update(Long id, ProductUpdateRequest req) {
        Product product = getById(id);
        product.setName(req.getName());
        product.setPrice(req.getPrice());
        return repository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        Product product = getById(id);
        repository.delete(product);
    }
}

