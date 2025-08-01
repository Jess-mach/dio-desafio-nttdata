package com.nttdata.produtos.service;

import com.nttdata.produtos.dto.ProdutoRequest;
import com.nttdata.produtos.dto.ProdutoResponse;
import com.nttdata.produtos.model.Produto;
import com.nttdata.produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    public ProdutoRepository repository;

    public Page<ProdutoResponse> listarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    public ProdutoResponse salvar(ProdutoRequest request) {
        Produto produto = new Produto();
        produto.setNome(request.nome());
        produto.setDescricao(request.descricao());
        produto.setPreco(request.preco());
        return toResponse(repository.save(produto));
    }

    public Optional<ProdutoResponse> buscarPorId(Long id) {
        return repository.findById(id).map(this::toResponse);
    }

    private ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco());
    }
}
