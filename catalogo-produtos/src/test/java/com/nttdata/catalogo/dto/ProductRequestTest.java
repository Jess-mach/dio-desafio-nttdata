package com.nttdata.catalogo.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private ProductRequest validRequest() {
        ProductRequest req = new ProductRequest();
        req.setName("Mouse");
        req.setDescription("Mouse sem fio");
        req.setPrice(new BigDecimal("59.90"));
        return req;
    }

    @Test
    @DisplayName("Quando ProductRequest é válido, não deve haver violações de validação")
    void testValidRequest_NoViolations() {
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(validRequest());
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Quando nome é blank, deve haver violação NotBlank")
    void testNameBlank_Violation() {
        ProductRequest req = validRequest();
        req.setName("   ");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(req);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly("O nome do produto é obrigatório");
    }

    @Test
    @DisplayName("Quando descrição é blank, deve haver violação NotBlank")
    void testDescriptionBlank_Violation() {
        ProductRequest req = validRequest();
        req.setDescription("");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(req);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly("A descrição do produto é obrigatória");
    }

    @Test
    @DisplayName("Quando preço é null, deve haver violação NotNull")
    void testPriceNull_Violation() {
        ProductRequest req = validRequest();
        req.setPrice(null);
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(req);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly("O preço do produto é obrigatório");
    }

    @Test
    @DisplayName("Quando preço é negativo, deve haver violação DecimalMin")
    void testPriceNegative_Violation() {
        ProductRequest req = validRequest();
        req.setPrice(new BigDecimal("-1.00"));
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(req);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly("O preço do produto deve ser maior ou igual a zero");
    }
}
