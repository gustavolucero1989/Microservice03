package com.example.serviceproduct.controller;

import com.example.serviceproduct.entity.Category;
import com.example.serviceproduct.entity.Product;
import com.example.serviceproduct.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    final ProductService productService;

    //Inyeccion de dependencias
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name = "categoryId",
            required = false) Long categoryId) {
        List<Product> products = new ArrayList<>();
        // Si la categoria es nula
        if (null == categoryId) {
            products = productService.listAllProduct();
            // Si es vacia la lista
            if (products.isEmpty()) {
                //Error 204
                return ResponseEntity.noContent().build();
            }
        } else {
            // findByCategory devuelve una lista de product (Implementacion en capa
            // ProductServiceImpl)
            products = productService.finByCategory(Category.builder().id(categoryId).build());
            //Si no encontramos la categoria
            if (products.isEmpty()) {
                // Error 404
                return ResponseEntity.notFound().build();
            }
        }
        // 200 OK
        return ResponseEntity.ok(products);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        Product product = productService.getProduct(id);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    //Valid se fija si es valido y sino manda el error a result
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result){
        if(result.hasErrors()){
            //lanza el error y termina el metodo
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Product productCreate = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        product.setId(id);
        Product productDB = productService.updateProduct(product);
        if(productDB==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){
        Product productDelete = productService.deleteProduct(id);
        if (productDelete == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDelete);
    }

    @GetMapping (value = "/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable  Long id ,
                                                      @RequestParam(name = "quantity",
                                                              required = true) Double quantity){
        Product product = productService.updateStock(id, quantity);
        if (product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    private String formatMessage(BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        //Intancio mi clase
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        //JAVA a JSON
        ObjectMapper mapper = new ObjectMapper();
        //Variable vacía
        String jsonString="";
        //Intenta hacer esto
        try {
            //Convierte java a json string
            jsonString = mapper.writeValueAsString(errorMessage);
        //Sino hace esto
            //Excepciones para json
        } catch (JsonProcessingException e) {
            //Error con clase y numero de linea
            e.printStackTrace();
        }
        return jsonString;
    }
}