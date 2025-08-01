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
        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setPreco(request.getPreco());
        Produto salvo = repository.save(produto);
        return new ProdutoResponse(salvo.getId(), salvo.getNome(), salvo.getDescricao(), salvo.getPreco());
    }

    public Page<ProdutoResponse> listar(Pageable pageable) {
        return repository.findAll(pageable).map(p ->
                new ProdutoResponse(p.getId(), p.getNome(), p.getDescricao(), p.getPreco()));
    }

    public Optional<ProdutoResponse> buscarPorId(Long id) {
        return repository.findById(id).map(p ->
                new ProdutoResponse(p.getId(), p.getNome(), p.getDescricao(), p.getPreco()));
    }

    public Optional<ProdutoResponse> atualizar(Long id, ProdutoRequest request) {
        return repository.findById(id).map(produto -> {
            produto.setNome(request.getNome());
            produto.setDescricao(request.getDescricao());
            produto.setPreco(request.getPreco());
            Produto atualizado = repository.save(produto);
            return new ProdutoResponse(atualizado.getId(), atualizado.getNome(), atualizado.getDescricao(), atualizado.getPreco());
        });
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
