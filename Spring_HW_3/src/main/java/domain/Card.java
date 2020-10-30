package domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "card")
public class Card {
    @Id
    @Column(name = "id")
    @GeneratedValue
    Long id;

    @OneToOne(mappedBy = "details") //привязал корзину к покупателю (связь 1 к 1)
    Client client;

    @ManyToMany  //сделал связь "многие ко многим" разные продукты могут лежать в разных корзинах
    @JoinTable(
            name = "products_in_cards",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id")
    )
    private List<Product> products;

    @Column(name = "numbers")
    private int numbers;


    public Card() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }
}
