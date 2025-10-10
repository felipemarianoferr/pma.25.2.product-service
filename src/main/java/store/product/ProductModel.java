package store.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "product")
@Getter @Setter @Accessors(chain = true, fluent = true)
@NoArgsConstructor @AllArgsConstructor
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "unit", nullable = false, length = 64)
    private String unit;

    @Column(name = "price", nullable = false)
    private Float price;

    public ProductModel(store.product.Product a) {
        // não setar id, deixa o banco gerar
        this.name = a.name();
        this.unit = a.unit();
        this.price = a.price();
    }

    public store.product.Product to() {
        return store.product.Product.builder()
            .id(this.id)
            .name(this.name)
            .unit(this.unit)
            .price(this.price)
            .build();
    }
}
