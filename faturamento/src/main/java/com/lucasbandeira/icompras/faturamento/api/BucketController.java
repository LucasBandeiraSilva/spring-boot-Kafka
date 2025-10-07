package com.lucasbandeira.icompras.faturamento.api;

import com.lucasbandeira.icompras.faturamento.bucket.BucketFile;
import com.lucasbandeira.icompras.faturamento.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bucket")
public class BucketController {

    private final BucketService service;

    @PostMapping
    public ResponseEntity<String> uploadFile( @RequestParam("file")MultipartFile file ){
        try (InputStream inputStream = file.getInputStream()){
            MediaType type = MediaType.parseMediaType(file.getContentType());
            var bucketFile = new BucketFile(file.getOriginalFilename(), inputStream, type, file.getSize());
            service.upload(bucketFile);
            return ResponseEntity.ok("Arquivo enviado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao enviar o arquivo: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> getUrl(@RequestParam String filename){
        try{
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).body(service.getUrl(filename));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao obter URL do arquivo: " + e.getMessage());
        }
    }
}
