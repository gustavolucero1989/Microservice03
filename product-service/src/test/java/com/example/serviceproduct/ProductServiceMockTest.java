package com.example.serviceproduct;

import com.example.serviceproduct.entity.Category;
import com.example.serviceproduct.entity.Product;
import com.example.serviceproduct.repository.ProductRepository;
import com.example.serviceproduct.service.ProductService;
import com.example.serviceproduct.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

//La clase de prueba basada en SpringBoot
@SpringBootTest
public class ProductServiceMockTest {
    //Los datos son simulados (mockeados) en lugar de estar
    //en la base de datos
    @Mock
    private ProductRepository productRepository;

    //Hacemos referencia a un objeto que implementa una interfaz
    private ProductService productService;

    //Se ejecuta antes del @Test
    @BeforeEach
    public void setup(){
        //Inicializamos la prueba Mock con Mockito
        MockitoAnnotations.initMocks(this);

        productService =  new ProductServiceImpl(productRepository);
        //Creamos un producto
        Product computer =  Product.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .price(Double.parseDouble("12.5"))
                .stock(Double.parseDouble("5"))
                .build();
        //Creamos el mock que cuando encuentre el id
        //retorne el objeto "computer"
        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(computer));
        //Cuando un producto es actualizado lo salvamos y lo retornamos
        Mockito.when(productRepository.save(computer)).thenReturn(computer);

    }

    //Comienza el Test
    @Test
    public void whenValidGetID_ThenReturnProduct(){
        //A la capa service que se comunica con la base de datos
        //le pedimos el producto 1L
        //getProduct esta desarrollada en ProductServiceImpl
        Product found = productService.getProduct(1L);
        //Validamos que coincida
        Assertions.assertThat(found.getName()).isEqualTo("computer");

    }

    @Test
    public void whenValidUpdateStock_ThenReturnNewStock(){
        Product newStock = productService.updateStock(1L,Double.parseDouble("8"));
        //Testeamos que el resultado obtenido es el esperado
        Assertions.assertThat(newStock.getStock()).isEqualTo(13);
    }
}
