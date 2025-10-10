package store.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ProductResource implements ProductController {

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<ProductOut> create(ProductIn in) {
        Product product = ProductParser.to(in);
        product.id(null); // id gerado pelo banco

        Product saved = productService.create(product);

        return ResponseEntity
            .created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.id())
                    .toUri()
            )
            .body(ProductParser.to(saved));
    }

    // a interface remota espera String
    @Override
    public ResponseEntity<ProductOut> findById(String id) {
        Long lid = Long.valueOf(id);
        return ResponseEntity.ok(
            ProductParser.to(productService.findById(lid))
        );
    }

    @Override
    public ResponseEntity<List<ProductOut>> findAll() {
        return ResponseEntity.ok(
            ProductParser.to(productService.findAll())
        );
    }

    // a interface remota espera String
    @Override
    public ResponseEntity<Void> delete(String id) {
        Long lid = Long.valueOf(id);
        productService.delete(lid);
        return ResponseEntity.noContent().build();
    }
}
