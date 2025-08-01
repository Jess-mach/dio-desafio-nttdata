package com.nttdata.produtos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDiscoveryClient
@SpringBootApplication
public class CatalogoProdutosApplication {
    public static void main(String[] args) {

        SpringApplication.run(CatalogoProdutosApplication.class, args);
    }
}
