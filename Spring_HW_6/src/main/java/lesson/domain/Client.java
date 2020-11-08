package lesson.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

//    @OneToOne
//    @JoinColumn(name = "details") //привязал корзину к покупателю (связь 1 к 1)
//    private Card details;

    public Client() {
    }

    public Client(Long id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return getId().equals(client.getId()) &&
                getName().equals(client.getName()) &&
                getEmail().equals(client.getEmail()) &&
                getPhoneNumber().equals(client.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,name, email, phoneNumber);
    }
    //    public Card getDetails() {
//        return details;
//    }
//
//    public void setDetails(Card details) {
//        this.details = details;
//    }
}
