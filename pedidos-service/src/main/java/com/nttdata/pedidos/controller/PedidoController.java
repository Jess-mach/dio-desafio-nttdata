package com.nttdata.pedidos.controller;

import com.nttdata.pedidos.dto.PedidoRequest;
import com.nttdata.pedidos.dto.PedidoResponse;
import com.nttdata.pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@RequestBody PedidoRequest request) {
        return ResponseEntity.status(201).body(service.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}
