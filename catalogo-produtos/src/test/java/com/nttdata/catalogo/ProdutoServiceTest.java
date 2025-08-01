package com.nttdata.catalogo;

import com.nttdata.catalogo.dto.ProdutoRequest;
import com.nttdata.catalogo.model.Produto;
import com.nttdata.catalogo.repository.ProdutoRepository;
import com.nttdata.catalogo.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProdutoServiceTest {

    private ProdutoRepository repository = mock(ProdutoRepository.class);
    private ProdutoService service = new ProdutoService();

    public ProdutoServiceTest() {
        service = new ProdutoService();
        service.getClass().getDeclaredFields()[0].setAccessible(true);
        try {
            service.getClass().getDeclaredFields()[0].set(service, repository);
        } catch (IllegalAccessException ignored) {}
    }

    @Test
    public void deveSalvarProduto() {
        ProdutoRequest req = new ProdutoRequest();
        req.setNome("Teste");
        req.setDescricao("Descricao");
        req.setPreco(BigDecimal.TEN);

        Produto salvo = new Produto();
        salvo.setId(1L);
        salvo.setNome("Teste");
        salvo.setDescricao("Descricao");
        salvo.setPreco(BigDecimal.TEN);

        when(repository.save(any())).thenReturn(salvo);

        var resp = service.salvar(req);
        assertEquals("Teste", resp.getNome());
    }

    @Test
    public void deveListarProdutos() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto");
        produto.setDescricao("Desc");
        produto.setPreco(BigDecimal.TEN);

        Page<Produto> page = new PageImpl<>(List.of(produto));
        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        var result = service.listar(PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void deveBuscarPorId() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto");
        produto.setDescricao("Desc");
        produto.setPreco(BigDecimal.TEN);

        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        var result = service.buscarPorId(1L);
        assertTrue(result.isPresent());
        assertEquals("Produto", result.get().getNome());
    }
}
