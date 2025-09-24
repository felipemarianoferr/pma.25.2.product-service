package store.account;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {

    private Logger looger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private AccountRepository accountRepository;

    public Product create(Product product) {
        if (null == product.name()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "name is mandatory!"
            );
        }
        // clean special caracters
        product.name(product.name().trim());
        if (product.name().length() < 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "name is too short!"
            );
        }
        if (null == product.price()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "price is mandatory!"
            );
        }

        if (null == product.unit()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "unit is mandatory!"
            );
        }

        if (productRepository.findByname(product.name()) != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "name already have been registered!"
            );

        product.sha256(hash(product.name()));

        return productRepository.save(
            new productModel(product)
        ).to();
    }

    public List<Product> findAll() {
        return StreamSupport.stream(
            productRepository.findAll().spliterator(), false)
            .map(productModel::to)
            .toList();
    }

    public Product findById(String id) {
        return productRepository.findById(id).map(productModel::to).orElse(null);
    }

    public void delete(String id) {
        productRepository.delete(new AccountModel().id(id));
    }
    
}
