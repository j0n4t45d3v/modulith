package com.jonatas.productmodule.controller;

import com.jonatas.productmodule.model.Product;
import com.jonatas.productmodule.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok(this.productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getOne(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.productRepository.findById(id).orElseThrow());
    }

    @PostMapping("/create")
    public ResponseEntity<Product> create(@RequestBody Product product){
        Product productCreated = productRepository.save(product);
        URI uri = UriComponentsBuilder.fromPath("/products/{id}").buildAndExpand(productCreated.getId()).toUri();
        return ResponseEntity.created(uri).body(productCreated);
    }

    @PutMapping("/{name}/add_quantity")
    public ResponseEntity<Product> addProduct(@PathVariable("name") String nameProduct, Long quantity){
        Product productFound = productAlreadyExist(nameProduct);

        long currentProductQuantity = productFound.getQuantity();
        long addingProduct = currentProductQuantity + quantity;
        productFound.setQuantity(addingProduct);

        return ResponseEntity.ok(productFound);
    }

    @PutMapping("/{name}/subtract_product")
    public ResponseEntity<Product> subtractProduct(@PathVariable("name") String nameProduct, Long quantity){
        Product productFound = productAlreadyExist(nameProduct);

        long currentProductQuantity = productFound.getQuantity();
        long addingProduct = currentProductQuantity - quantity;
        productFound.setQuantity(addingProduct);

        return ResponseEntity.ok(productFound);
    }


    @DeleteMapping("/{name}/delete_product")
    public ResponseEntity<Void> dropProduct(@PathVariable("name") String nameProduct){
        Product productFound = this.productAlreadyExist(nameProduct);
        this.productRepository.delete(productFound);
        return ResponseEntity.ok().build();
    }

    private Product productAlreadyExist(String nameProduct){
        return this.productRepository.findByName(nameProduct).orElseThrow();
    }
}
