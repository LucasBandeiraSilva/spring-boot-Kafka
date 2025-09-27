package com.lucasbandeira.icompras.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.lucasbandeira.icompras.pedidos.client")
public class ClientsConfig {
}
