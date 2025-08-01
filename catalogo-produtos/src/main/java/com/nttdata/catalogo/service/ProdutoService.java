package com.nttdata.catalogo.service;

import com.nttdata.catalogo.dto.ProdutoRequest;
import com.nttdata.catalogo.dto.ProdutoResponse;
import com.nttdata.catalogo.model.Produto;
import com.nttdata.catalogo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public ProdutoResponse salvar(ProdutoRequest request) {
        Produto produto = new Produto();
        produto.setName(request.getName());
        produto.setDescription(request.getDescription());
        produto.setPrice(request.getPrice());
        Produto salvo = repository.save(produto);
        return new ProdutoResponse(salvo.getId(), salvo.getName(), salvo.getDescription(), salvo.getPrice());
    }

    public Page<ProdutoResponse> listar(Pageable pageable) {
        return repository.findAll(pageable).map(p ->
                new ProdutoResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice()));
    }

    public Optional<ProdutoResponse> buscarPorId(Long id) {
        return repository.findById(id).map(p ->
                new ProdutoResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice()));
    }

    public Optional<ProdutoResponse> atualizar(Long id, ProdutoRequest request) {
        return repository.findById(id).map(produto -> {
            produto.setName(request.getName());
            produto.setDescription(request.getDescription());
            produto.setPrice(request.getPrice());
            Produto atualizado = repository.save(produto);
            return new ProdutoResponse(atualizado.getId(), atualizado.getName(), atualizado.getDescription(), atualizado.getPrice());
        });
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
