package com.nttdata.catalogo.service;

import com.nttdata.catalogo.dto.ProductRequest;
import com.nttdata.catalogo.dto.ProductResponse;
import com.nttdata.catalogo.model.ProductEntity;
import com.nttdata.catalogo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    private ProductRequest reqDto;
    private ProductEntity entity;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        reqDto = new ProductRequest();
        reqDto.setName("Caneta");
        reqDto.setDescription("Caneta azul");
        reqDto.setPrice(new BigDecimal("5.00"));

        entity = new ProductEntity(null,
                reqDto.getName(),
                reqDto.getDescription(),
                reqDto.getPrice());

        pageable = PageRequest.of(0, 10);
    }

    @Test
    @DisplayName("listar: deve retornar uma página de ProductResponse mapeada")
    void testListar_ReturnsPageOfResponses() {
        List<ProductEntity> products = List.of(
                new ProductEntity(1L, "A", "Desc A", new BigDecimal("1.00")),
                new ProductEntity(2L, "B", "Desc B", new BigDecimal("2.00"))
        );
        Page<ProductEntity> pageEnt = new PageImpl<>(products, pageable, products.size());
        when(repository.findAll(pageable)).thenReturn(pageEnt);

        Page<ProductResponse> result = service.list(pageable);

        assertThat(result).hasSize(2);
        assertThat(result.getContent())
                .extracting(ProductResponse::id, ProductResponse::name)
                .containsExactly(
                        tuple(1L, "A"),
                        tuple(2L, "B")
                );
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("buscar: quando existe, deve retornar Optional com ProductResponse")
    void testBuscar_WhenFound_ReturnsOptionalResponse() {
        ProductEntity saved = new ProductEntity(10L, "X", "Desc X", new BigDecimal("9.99"));
        when(repository.findById(10L)).thenReturn(Optional.of(saved));

        Optional<ProductResponse> opt = service.getById(10L);

        assertThat(opt).isPresent();
        ProductResponse resp = opt.get();
        assertThat(resp.id()).isEqualTo(10L);
        assertThat(resp.name()).isEqualTo("X");
        verify(repository).findById(10L);
    }

    @Test
    @DisplayName("buscar: quando não existe, deve retornar Optional.empty")
    void testBuscar_WhenNotFound_ReturnsEmptyOptional() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<ProductResponse> opt = service.getById(99L);

        assertThat(opt).isEmpty();
        verify(repository).findById(99L);
    }

    @Test
    @DisplayName("criar: deve salvar a entidade e retornar ProductResponse com ID")
    void testCriar_SavesEntityAndReturnsResponse() {
        ProductEntity toSave = new ProductEntity(null,
                reqDto.getName(),
                reqDto.getDescription(),
                reqDto.getPrice());
        ProductEntity saved = new ProductEntity(5L,
                reqDto.getName(),
                reqDto.getDescription(),
                reqDto.getPrice());
        when(repository.save(any(ProductEntity.class))).thenReturn(saved);

        ProductResponse resp = service.create(reqDto);

        assertThat(resp.id()).isEqualTo(5L);
        assertThat(resp.name()).isEqualTo(reqDto.getName());
        verify(repository).save(argThat(p ->
                p.getName().equals(reqDto.getName()) &&
                        p.getDescription().equals(reqDto.getDescription()) &&
                        p.getPrice().compareTo(reqDto.getPrice()) == 0
        ));
    }

    @Test
    @DisplayName("atualizar: quando existe, deve atualizar e retornar Optional com ProductResponse")
    void testAtualizar_WhenFound_UpdatesAndReturnsResponse() {
        ProductEntity existing = new ProductEntity(7L, "Old", "Old Desc", new BigDecimal("7.00"));
        when(repository.findById(7L)).thenReturn(Optional.of(existing));
        when(repository.save(any(ProductEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductRequest updateDto = new ProductRequest();
        updateDto.setName("New");
        updateDto.setDescription("New Desc");
        updateDto.setPrice(new BigDecimal("8.50"));

        Optional<ProductResponse> opt = service.update(7L, updateDto);

        assertThat(opt).isPresent();
        ProductResponse resp = opt.get();
        assertThat(resp.id()).isEqualTo(7L);
        assertThat(resp.name()).isEqualTo("New");
        assertThat(resp.description()).isEqualTo("New Desc");
        assertThat(resp.price()).isEqualByComparingTo("8.50");
        verify(repository).findById(7L);
        verify(repository).save(existing);
    }

    @Test
    @DisplayName("atualizar: quando não existe, deve retornar Optional.empty")
    void testAtualizar_WhenNotFound_ReturnsEmptyOptional() {
        when(repository.findById(123L)).thenReturn(Optional.empty());

        Optional<ProductResponse> opt = service.update(123L, reqDto);

        assertThat(opt).isEmpty();
        verify(repository).findById(123L);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("excluir: deve chamar deleteById no repositório")
    void testExcluir_CallsRepository() {
        doNothing().when(repository).deleteById(33L);

        service.delete(33L);

        verify(repository).deleteById(33L);
    }
}
