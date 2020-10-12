package com.example.serviceproduct;

import com.example.serviceproduct.entity.Category;
import com.example.serviceproduct.entity.Product;
import com.example.serviceproduct.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Date;
import java.util.List;

//Pruebas transaccionales se reviertes al final de la prueba
@DataJpaTest
public class ProductRepositoryMockTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    //Cuando busquemos por una categoria esperamos una lista de Productos
    public void whenFindByCategory_thenReturnListProduct(){
        //Creamos un producto
        Product product01 = Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("20"))//parseDouble toma un String
                .price(Double.parseDouble("1240.99"))
                .status("Created")
                .createAt(new Date()).build();// struct Objeto.builder().atributos.build()
        productRepository.save(product01); //  Guardamos nuestro producto en ls base de datos

        //Lista de productos de la categoria encontrada(findByCategory) en este caso "shoes"
        //por data.sql insertamos 2 productos
        //por test insertamos 1 producto mas
        //entonces founds es una lista de 3 productos
        List<Product> founds= productRepository.findByCategory(product01.getCategory());

        // Testeo de afirmación, si el .save del producto estaria mal
        // nos arrojaria un error
        Assertions.assertThat(founds.size()).isEqualTo(3);
    }
}
