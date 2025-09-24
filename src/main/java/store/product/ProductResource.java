package store.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ProductResource implements AccountController {

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<ProductOut> create(ProductIn in) {
        // parser AccountIn to Account
        Product product = ProductParser.to(in);

        Product saved = productService.create(account);

        // parser Account to AccountOut and build to
        // HATEAOS standard
        return ResponseEntity
            .created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.id())
                    .toUri()
            ).body(AccountParser.to(saved));
    }

    @Override
    public ResponseEntity<ProductOut> findById(String id) {
        return ResponseEntity
            .ok(ProductParser.to(productService.findById(id)));
    }

    @Override
    public ResponseEntity<List<ProductOut>> findAll() {
        return ResponseEntity
            .ok()
            .body(ProductParser.to(productService.findAll()));
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        return ResponseEntity
            .noContent()
            .build();
    }    
}
