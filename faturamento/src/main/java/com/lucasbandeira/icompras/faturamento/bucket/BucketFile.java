package com.lucasbandeira.icompras.faturamento.bucket;

import org.springframework.http.MediaType;

import java.io.InputStream;

public record BucketFile(String name, InputStream inputStream, MediaType type, long size) {
}
