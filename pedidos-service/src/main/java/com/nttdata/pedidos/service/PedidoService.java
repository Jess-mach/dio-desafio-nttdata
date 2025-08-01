package com.nttdata.pedidos.service;

import com.nttdata.pedidos.dto.*;
import com.nttdata.pedidos.model.Pedido;
import com.nttdata.pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private final String PRODUTO_SERVICE_URL = "http://produto-service:8080/produtos/";

    public PedidoResponse criar(PedidoRequest request) {
        List<ProdutoResponse> produtos = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Long id : request.produtos()) {
            ProdutoResponse produto = restTemplate.getForObject(PRODUTO_SERVICE_URL + id, ProdutoResponse.class);
            produtos.add(produto);
            total = total.add(produto.preco());
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(request.cliente());
        pedido.setData(LocalDate.now());
        pedido.setProdutos(request.produtos());
        pedido.setTotal(total);

        repository.save(pedido);

        return new PedidoResponse(pedido.getId(), pedido.getCliente(), pedido.getData(), produtos, pedido.getTotal());
    }

    public List<PedidoResponse> listar() {
        return repository.findAll().stream().map(p -> {
            List<ProdutoResponse> produtos = p.getProdutos().stream().map(id ->
                restTemplate.getForObject(PRODUTO_SERVICE_URL + id, ProdutoResponse.class)
            ).toList();
            return new PedidoResponse(p.getId(), p.getCliente(), p.getData(), produtos, p.getTotal());
        }).toList();
    }
}
