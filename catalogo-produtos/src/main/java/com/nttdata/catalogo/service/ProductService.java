package com.nttdata.catalogo.service;

import com.nttdata.catalogo.dto.ProductRequest;
import com.nttdata.catalogo.dto.ProductResponse;
import com.nttdata.catalogo.model.ProductEntity;
import com.nttdata.catalogo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public ProductResponse create(ProductRequest request) {
        ProductEntity produto = new ProductEntity();
        produto.setName(request.getName());
        produto.setDescription(request.getDescription());
        produto.setPrice(request.getPrice());
        ProductEntity salvo = repository.save(produto);
        return new ProductResponse(salvo.getId(), salvo.getName(), salvo.getDescription(), salvo.getPrice());
    }

    public Page<ProductResponse> list(Pageable pageable) {
        return repository.findAll(pageable).map(p ->
                new ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice()));
    }

    public Optional<ProductResponse> getById(Long id) {
        return repository.findById(id).map(p ->
                new ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice()));
    }

    public Optional<ProductResponse> update(Long id, ProductRequest request) {
        return repository.findById(id).map(produto -> {
            produto.setName(request.getName());
            produto.setDescription(request.getDescription());
            produto.setPrice(request.getPrice());
            ProductEntity atualizado = repository.save(produto);
            return new ProductResponse(atualizado.getId(), atualizado.getName(), atualizado.getDescription(), atualizado.getPrice());
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
